from matplotlib import pyplot as plt

with open('../cube_opt_100000_20_result.txt') as f:
    x = []
    y = []
    c = 1
    for line in f:
        n, m = [int(i) for i in line.split()]
        Tf = int(next(f))
        Tb = int(next(f))
        word = next(f)
        if m == 20:
            x.append(c)
            c += 1
            y.append(Tb)
            if c == 100:
                break



with open('../cube_100000_20_result.txt') as f:
    xx = []
    yy = []
    c = 1
    for line in f:
        n, m = [int(i) for i in line.split()]
        Tf = int(next(f))
        Tb = int(next(f))
        word = next(f)
        if m == 20:
            xx.append(c)
            c += 1
            yy.append(Tb)
            if c == 100:
                break

plt.title('M = 20')
plt.plot(x, y, 'bo', label = 'M = 20 opt')
plt.plot(xx, yy, 'ro', label = 'M = 20')
# plt.semilogx(x, [0] * len(x), 'r-', label = 'y = 0')
# plt.plot(x, y, 'b.', label='M = 20')
plt.xlabel('id')
plt.ylabel('Tb')
plt.legend(loc = 3)

plt.ylim(0, 100000)
# plt.xlim(0, 110000)
plt.show()


