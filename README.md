# Hybridizing Non-dominated Sorting Algorithms: Divide-and-Conquer Meets Best Order Sort

##The Divide-and-Conquer Approach

Алгоритм Буздалова использует подход "разделяй и властвуй" и имеет время работы 
O(N(logM)^{M-1}). Опишем его в кратце:
В каждый момент времени 



##Best Order Sort


- сбор данных для проверки (NAMETEST_N_M_data.txt)
    Формат вывода:
    id N M
    N строчек по M double
- сбор времени работы BOS алгоритма на этих же данных (NAMETEST_N_M_time_bos.txt)
    Формат вывода:
    id N M t n k
    id - соответствует id из первого запуска
    t - время в наносекундах
    n - количество запусков алгоритма
    k - количество ранов
- сбор времени работы Fast алгоритма на этих данных (NAMETEST_N_M_time_fast.txt)
    Формат вывода:
    id N M t n k
- агрегирование данных.
 Сравнение скоростей:
 (NAMETEST_N_M_time_result.txt)
    Фомат вывода:
    N M
    speed_fast (time_fast / n_fast)
    speed_bos (time_bos / n_bos)
    k

Tests:
- cube_10^5_* - Hypercube random double.
Tests:
- one_rank_10^5_* - all points have one rank





