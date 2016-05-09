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


# - figure_m=4.png
# n1 = 7
# n2 = 1560
# - figure_m=6.png
# n1 = 11
# n2 = 9860
# - figure_m=8.png
# n1 = 11
# n2 = 15800
# - figure_m=10.png
# n1 = 13
# n2 = 14200
# - figure_m=12.png
# n1 = 17
# n2 = 8300
# - figure_m=14.png
# n1 = 17
# n2 = 6250
# - figure_m=16.png
# n1 = 23
# n2 = 4500
# - figure_m=18.png
# n1 = 27
# n2 = 5500
# - figure_m=20.png
# n1 = 24
# n2 = 6250
