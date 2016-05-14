from matplotlib import pyplot as plt

x =  [4,   6,   8,    10,   12,  14,  16,   17,   18,   19,   20,   21,   25,    30,    35,    40]
n1 = [7,   11,  11,   13,   17,  17,  23,   22,   27,   33,   33,   37,   39,    70,    96,    194]
n2 = [1560,9860,15800,14200,8300,6250,4500, 5532, 5500, 6250, 6250, 7100, 10000, 27177, 28900, 29900]

plt.title('cube, M = 4 ... 20')
plt.plot(x, n2, 'b.-', label = 'n2')
# plt.semilogx(x, [0] * len(x), 'r-', label = 'y = 0')
# plt.plot(x, y, 'b.', label='M = 20')
plt.xlabel('m')
plt.ylabel('n2')
plt.legend(loc = 4)

# plt.xlim(0, 21)
plt.show()
#
# - cube_m=17.png
# n1 =22
# n2 =5532