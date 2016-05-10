from matplotlib import pyplot as plt

x = [4, 6, 8, 10, 12, 14, 16, 18, 20]
n1 = [7,11,11,13,17,17,23,27,24]
n2 = [1560,9860,15800,14200,8300,6250,4500,5500,6250]

plt.title('cube, M = 4 ... 20')
plt.plot(x, n1, 'b-', label = 'n1')
# plt.semilogx(x, [0] * len(x), 'r-', label = 'y = 0')
# plt.plot(x, y, 'b.', label='M = 20')
plt.xlabel('m')
plt.ylabel('n1')
plt.legend(loc = 4)

plt.xlim(0, 21)
plt.show()

