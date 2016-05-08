from matplotlib import pyplot as plt

# не очень нужное сравнение opt и не opt

with open("cube_opt_100000_20_result.txt") as f:
    x = []
    y = []
    for line in f:
        n, m = [int(i) for i in line.split()]
        Tf = int(next(f))
        Tb = int(next(f))
        word = next(f)
        if m == 20:
            x.append(n)
            y.append((Tb - Tf) / max(Tf, Tb))

with open('cube_100000_20_result.txt') as f:
    xx = []
    yy = []
    for line in f:
        n, m = [int(i) for i in line.split()]
        Tf = int(next(f))
        Tb = int(next(f))
        word = next(f)
        if m == 20:
            x.append(n)
            y.append((Tb - Tf) / max(Tf, Tb))

plt.title('M = 20')
plt.semilogx(x, y, 'bo', label = 'M = 20 opt')
plt.semilogx(xx, yy, 'ro', label = 'M = 20')
plt.semilogx(x, [0] * len(x), 'r-', label = 'y = 0')

plt.xlabel('N')
plt.ylabel('t')
plt.legend(loc = 3)

plt.xlim(0, 110000)
plt.show()


