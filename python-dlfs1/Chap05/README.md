# Chap05: 오차역전파법

이번 장에서는 가중치 매개변수의 기울기를 효율적으로 계산하는 <b>오차역전파법(backpropagation)</b>을 배워보도록 하겠다.

### 연쇄법칙
계산 그래프 상에서 순전파는 계산 결과를 왼쪽에서 오른쪽으로 전달했다. 역전파는 '국소적인 미분'을 오른쪽에서 왼쪽으로 전달하는데, <b>연쇄법칙(chain rule)</b>의 원리를 이용한다.  
x → t → z로 가는 순전파에 대해 z → t → x의 역전파를 구해보면, z → x의 역전파는 $\frac{\partial z}{\partial x}$를 구해야 한다. 이는 연쇄법칙에 의해 $\frac{\partial z}{\partial z} \frac{\partial z}{\partial t} \frac{\partial t}{\partial x}$를 구하는 문제가 된다.  
덧셈 노드의 역전파에 대해 알아보자. $z = x + y$라는 식이 있을 때, $\frac{\partial z}{\partial x} = 1$이고, $\frac{\partial z}{\partial y} = 1$이다. 즉, 상류의 값을 그대로 흘려보내면 된다.  
곱셈 노드의 역전파에서는 $z = xy$라는 식이 있을 때, $\frac{\partial z}{\partial x} = y$이고, $\frac{\partial z}{\partial y} = x$다. 곱셈의 역전파는 순방향 입력 신호의 값이 필요하므로, 구현 시 순전파의 입력 신호를 변수에 저장해 두어야 한다. 