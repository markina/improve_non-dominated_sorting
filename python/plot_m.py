from matplotlib import pyplot as plt

# постоение графика по name N M

# N = 100000
N = 100
M = 5
name = "cube"
prefix = "_result.txt"

full_name = name + "_" + str(N) + "_" + str(M) + prefix

with open(full_name) as f:
    x = []
    y = []
    for line in f:
        n, m, k = [int(i) for i in line.split()]
        Tf = int(next(f))
        Tb = int(next(f))
        if m == M:
            x.append(n)
            y.append((Tb - Tf) / max(Tf, Tb))

plt.title(name)
plt.semilogx(x, y, 'bo', label = 'M = ' + str(M))
plt.semilogx(x, [0] * len(x), 'r-', label = 'y = 0')
# plt.plot(x, y, 'b.', label='M = 20')
plt.xlabel('N')
plt.ylabel('t')
plt.legend(loc = 1)

# plt.xlim(0, 110000)
# plt.ylim(0, 1.05)
plt.show()
