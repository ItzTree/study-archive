# Chap05: 오차역전파법

이번 장에서는 가중치 매개변수의 기울기를 효율적으로 계산하는 <b>오차역전파법(backpropagation)</b>을 배워보도록 하겠다.

### 연쇄법칙
계산 그래프 상에서 순전파는 계산 결과를 왼쪽에서 오른쪽으로 전달했다. 역전파는 '국소적인 미분'을 오른쪽에서 왼쪽으로 전달하는데, <b>연쇄법칙(chain rule)</b>의 원리를 이용한다.  
x → t → z로 가는 순전파에 대해 z → t → x의 역전파를 구해보면, z → x의 역전파는 $\frac{\partial z}{\partial x}$를 구해야 한다. 이는 연쇄법칙에 의해 $\frac{\partial z}{\partial z} \frac{\partial z}{\partial t} \frac{\partial t}{\partial x}$를 구하는 문제가 된다.  
덧셈 노드의 역전파에 대해 알아보자. $z = x + y$라는 식이 있을 때, $\frac{\partial z}{\partial x} = 1$이고, $\frac{\partial z}{\partial y} = 1$이다. 즉, 상류의 값을 그대로 흘려보내면 된다.  
곱셈 노드의 역전파에서는 $z = xy$라는 식이 있을 때, $\frac{\partial z}{\partial x} = y$이고, $\frac{\partial z}{\partial y} = x$다. 곱셈의 역전파는 순방향 입력 신호의 값이 필요하므로, 구현 시 순전파의 입력 신호를 변수에 저장해 두어야 한다.  

### 활성화 함수 계층 구현하기
활성화 함수로 사용되는 ReLU 식은 다음과 같다.
```math
y = 
\begin{cases}
    x & (x > 0) \\
    0 & (x \le 0)
\end{cases}
```
```math
\frac{\partial y}{\partial x} =
\begin{cases}
    1 & (x > 0) \\
    0 & (x \le 0)
\end{cases}
```

활성화 함수로 사용되는 시그모이드 함수는 다음과 같다.
```math
y = \frac{1}{1 + exp(-x)}
```
```math
\frac{\partial y}{\partial x} = \frac{exp(-x)}{(1+exp(-x))^2} = y(1-y)
```

### Affine 계층
신경망의 순전파에서 가중치 신호의 총합을 계산하기 위해 $Y = X \cdot W + B$와 같은 식을 이용했다. 이를 계산 그래프로 나타내면 $X$와 $W$를 곱하는 노드와 $X \cdot W$와 B를 더하는 노드가 있을 것이다. $Y = X \cdot W$의 역전파를 구해보도록 하겠다.  
```math
\begin{pmatrix}
y_{11} & y_{12} \\
y_{21} & y_{22}
\end{pmatrix}
= 
\begin{pmatrix}
x_{11} & x_{12} & x_{13} \\
x_{21} & x_{22} & x_{23} 
\end{pmatrix}
\cdot
\begin{pmatrix}
w_{11} & w_{12} \\
w_{21} & w_{22} \\
w_{31} & w_{32} \\
\end{pmatrix}
```
여기서, $y_{11} = x_{11}w_{11} + x_{12}w_{21} + x_{13}w_{31}$로 나타낼 수 있다. 여기서 $y_{11}$을 $x_{11}$에 대해 미분하면 $\dfrac{\partial y_{11}}{\partial x_{11}} = w_{11}$이 된다.  
구하고자 하는 값은 Loss를 $x_{11}$로 편미분한 값인 $\frac{\partial L}{\partial x_{11}}$이다. 그렇다면 연쇄법칙을 이용하여 다음과 같은 수식을 구할 수 있다.  
```math
\frac{\partial L}{\partial x_{11}}
= \frac{\partial y_{11}}{\partial x_{11}}\frac{\partial L}{\partial y_{11}} + \frac{\partial y_{12}}{\partial x_{11}}\frac{\partial L}{\partial y_{12}} 
= w_{11} \frac{\partial L}{\partial y_{11}} + w_{12} \frac{\partial L}{\partial y_{12}}
```
$\frac{\partial L}{\partial x_{11}}$은 $x_{11}$이 아주 조금 변했을 때, $L$이 얼마나 변했는지를 알 수 있다. 위 행렬에서 $x_{11}$이 변하면 $y_{11}$과 $y_{12}$를 통해 $L$이 변하게 되므로, $\frac{\partial L}{\partial x_{11}}$은 $y_{11}$과 $y_{12}$에 따른 변화율 합을 통해 구해야 한다.  
```math
\begin{align}
\frac{\partial L}{\partial X} 
& = \begin{pmatrix}
\frac{\partial L}{\partial x_{11}} &
\frac{\partial L}{\partial x_{12}} &
\frac{\partial L}{\partial x_{13}} \\ \\
\frac{\partial L}{\partial x_{21}} &
\frac{\partial L}{\partial x_{22}} &
\frac{\partial L}{\partial x_{23}} 
\end{pmatrix} \\ 
& = \begin{pmatrix}
\frac{\partial L}{\partial y_{11}} w_{11} + \frac{\partial L}{\partial y_{12}} w_{12} &
\frac{\partial L}{\partial y_{11}} w_{21} + \frac{\partial L}{\partial y_{12}} w_{22} &
\frac{\partial L}{\partial y_{11}} w_{31} + \frac{\partial L}{\partial y_{12}} w_{32} \\ \\
\frac{\partial L}{\partial y_{21}} w_{11} + \frac{\partial L}{\partial y_{22}} w_{12} &
\frac{\partial L}{\partial y_{21}} w_{21} + \frac{\partial L}{\partial y_{22}} w_{22} &
\frac{\partial L}{\partial y_{21}} w_{31} + \frac{\partial L}{\partial y_{22}} w_{32}
\end{pmatrix} \\
& = \begin{pmatrix}
\frac{\partial L}{\partial y_{11}} &
\frac{\partial L}{\partial y_{12}} \\ \\
\frac{\partial L}{\partial y_{21}} &
\frac{\partial L}{\partial y_{22}} 
\end{pmatrix}
\begin{pmatrix}
w_{11} & w_{21} & w_{31} \\
w_{12} & w_{22} & w_{32}
\end{pmatrix} \\
& = 
\frac{\partial L}{\partial Y} \ \cdot W^T 
\end{align}
```
마찬가지로, $\dfrac{\partial L}{\partial W} = W^T \cdot \dfrac{\partial L}{\partial Y}  $라는 식을 유도할 수 있다.