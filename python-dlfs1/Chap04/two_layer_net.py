import numpy as np 
from functions import *

class TwoLayerNet:
    # input_size: 입력 뉴런 수, hidden_size: 은닉층 뉴런 수
    # output_size: 출력 뉴런 수
    def __init__(self, input_size, hidden_size, output_size, weight_init_std=0.01):
        self.params={}
        self.params['W1'] = weight_init_std * np.random.randn(input_size, hidden_size)
        self.params['b1'] = np.zeros(hidden_size)
        self.params['W2'] = weight_init_std * np.random.randn(hidden_size, output_size)
        self.params['b2'] = np.zeros(output_size)

    # 추론(순전파)
    # 입력값 X를 신경망에 통과시켜 결과 Y를 얻음
    def predict(self, x):
        W1, W2 = self.params['W1'], self.params['W2']
        b1, b2 = self.params['b1'], self.params['b2']

        a1 = np.dot(x, W1) + b1
        z1 = sigmoid(a1)
        a2 = np.dot(z1, W2) + b2 
        y = softmax(a2)

        return y
    
    # x: 입력 데이터, t: 정답 레이블
    # 예측 결과 Y를 갖고 손실 함수값을 얻어냄
    def loss(self, x, t):
        y = self.predict(x)

        return cross_entropy_error(y, t)
    
    # 정확도(0~1)를 구함
    def accuracy(self, x, t):
        y = self.predict(x)
        y = np.argmax(y, axis=1)
        t = np.arange(t, axis=1)

        accuracy = np.sum(y == t) / float(x.shape[0])
        return accuracy
    
    # 기울기를 구함
    def numerical_gradient(self, x, t):
        loss_W = lambda W: self.loss(x, t)

        grads = {}
        grads['W1'] = numerical_gradient(loss_W, self.params['W1'])
        grads['b1'] = numerical_gradient(loss_W, self.params['b1'])
        grads['W2'] = numerical_gradient(loss_W, self.params['W2'])
        grads['b2'] = numerical_gradient(loss_W, self.params['b2'])

        return grads