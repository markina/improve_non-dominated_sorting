package main.java;

/**
 * Created by mmarkina on 05/06/16.
 */

public class ChangePointEvaluation {
    static int numFront; // if cube: numFront = 0

    Borders [] front0 = new Borders[]{
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(10, 66, 614),
            new Borders(8, 108, 4068),
            new Borders(11, 130, 6079),
            new Borders(15, 286, 8450),
            new Borders(18, 234, 12464),
            new Borders(18, 875, 13235),
            new Borders(21, 334, 12885),
            new Borders(22, 175, 9249),
            new Borders(26, 153, 7182),
            new Borders(26, 486, 5720),
            new Borders(30, 191, 3950),
            new Borders(36, 406, 3802),
            new Borders(34, 286, 3542),
            new Borders(34, 251, 3221),
            new Borders(38, 427, 3550),
            new Borders(36, 269, 3582),
            new Borders(42, 305, 3845),
            new Borders(45, 494, 3851),
            new Borders(48, 520, 4056),
            new Borders(62, 413, 4009),
            new Borders(59, 699, 3849),
            new Borders(65, 915, 4943),
            new Borders(77, 772, 4783),
            new Borders(89, 875, 4298),
            new Borders(81, 627, 4921),
            new Borders(96, 633, 5357),
            new Borders(96, 638, 5598),
            new Borders(111, 1041, 5766)
    };


    Borders [] front1 = new Borders[] {
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(10, 11, 11), // 3
            new Borders(21, 55, 87),
            new Borders(16, 56, 258),
            new Borders(18, 70, 540),
            new Borders(18, 80, 821),
            new Borders(20, 78, 1098),
            new Borders(22, 63, 1411),
            new Borders(21, 111, 1760),
            new Borders(24, 108, 1920),
            new Borders(28, 176, 2343),
            new Borders(32, 100, 2514),
            new Borders(34, 250, 2851),
            new Borders(36, 622, 3293),
            new Borders(36, 253, 3461),
            new Borders(37, 301, 3816),
            new Borders(39, 176, 4195),
            new Borders(39, 963, 4750),
            new Borders(46, 423, 4609), // 20
            new Borders(56, 330, 5195),
            new Borders(62, 970, 5225),
            new Borders(44, 714, 5189),
            new Borders(83, 798, 5679),
            new Borders(88, 431, 6029),
            new Borders(91, 663, 5951),
            new Borders(109, 762, 6435),
            new Borders(90, 741, 6905),
            new Borders(118, 1212, 7279),
            new Borders(141, 1127, 7251)     // 30
    };
    
    Borders [] front2 = new Borders[] {
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(14, 15, 15), // 3
            new Borders(14, 31, 102),
            new Borders(15, 70, 254),
            new Borders(17, 77, 578),
            new Borders(20, 112, 841),
            new Borders(20, 79, 1018),
            new Borders(21, 187, 1524),
            new Borders(23, 95, 1616),
            new Borders(25, 96, 1933),
            new Borders(28, 233, 2262),
            new Borders(27, 208, 2321),
            new Borders(31, 152, 2734),
            new Borders(35, 336, 3250),
            new Borders(38, 308, 3227),
            new Borders(40, 216, 3645),
            new Borders(42, 253, 4184),
            new Borders(46, 405, 4510),
            new Borders(48, 622, 4569),
            new Borders(46, 665, 5121),
            new Borders(63, 727, 4788),
            new Borders(70, 562, 5155),
            new Borders(79, 858, 5504),
            new Borders(88, 658, 5782),
            new Borders(102, 964, 5909),
            new Borders(96, 816, 6204),
            new Borders(109, 1088, 6827),
            new Borders(130, 991, 6941),
            new Borders(137, 825, 6243) // 30
    };
 
    Borders [] front3 = new Borders[] {
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(6, 11, 12),
            new Borders(10, 23, 96),
            new Borders(10, 63, 247),
            new Borders(15, 68, 494),
            new Borders(17, 86, 816),
            new Borders(15, 91, 1257),
            new Borders(22, 103, 1358),
            new Borders(24, 76, 1623),
            new Borders(25, 161, 1795),
            new Borders(26, 101, 2065),
            new Borders(32, 255, 2761),
            new Borders(33, 210, 2570),
            new Borders(36, 216, 3166),
            new Borders(34, 343, 3113),
            new Borders(38, 469, 3653),
            new Borders(43, 349, 4148),
            new Borders(43, 310, 4648),
            new Borders(45, 362, 4376),
            new Borders(55, 579, 4838),
            new Borders(71, 579, 4320),
            new Borders(61, 490, 5020),
            new Borders(55, 576, 5402),
            new Borders(80, 629, 6010),
            new Borders(88, 768, 5551),
            new Borders(104, 883, 6493),
            new Borders(135, 804, 6151),
            new Borders(131, 1075, 6315),
            new Borders(139, 1102, 5975)
    };


    Borders [] front5 = new Borders[] {
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(5, 7, 17),
            new Borders(6, 29, 111),
            new Borders(10, 66, 253),
            new Borders(10, 85, 526),
            new Borders(14, 70, 912),
            new Borders(19, 81, 1105),
            new Borders(20, 71, 1533),
            new Borders(20, 118, 1611),
            new Borders(23, 217, 1786),
            new Borders(23, 111, 2120),
            new Borders(28, 202, 2767),
            new Borders(31, 219, 2604),
            new Borders(30, 252, 2733),
            new Borders(38, 353, 3320),
            new Borders(38, 194, 3770),
            new Borders(41, 336, 4060),
            new Borders(44, 377, 4332),
            new Borders(44, 408, 4155),
            new Borders(48, 503, 5134),
            new Borders(62, 724, 4400),
            new Borders(71, 675, 5142),
            new Borders(91, 451, 4791),
            new Borders(64, 431, 6029),
            new Borders(89, 1245, 5527),
            new Borders(100, 995, 6592),
            new Borders(119, 900, 5879),
            new Borders(129, 1313, 6666),
            new Borders(126, 1084, 6460)
    };

    Borders [] front10 = new Borders[]{
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(4, 13, 35),
            new Borders(5, 22, 138),
            new Borders(6, 57, 259),
            new Borders(10, 69, 536),
            new Borders(10, 66, 792),
            new Borders(11, 65, 1059),
            new Borders(12, 97, 1539),
            new Borders(13, 100, 1599),
            new Borders(16, 88, 1897),
            new Borders(18, 96, 2062),
            new Borders(23, 123, 2844),
            new Borders(21, 81, 2692),
            new Borders(28, 499, 3085),
            new Borders(27, 461, 3779),
            new Borders(34, 373, 3860),
            new Borders(40, 332, 4033),
            new Borders(38, 473, 4077),
            new Borders(40, 593, 4222),
            new Borders(44, 564, 4621),
            new Borders(44, 981, 4161),
            new Borders(46, 807, 5155),
            new Borders(62, 538, 5079),
            new Borders(70, 765, 5726),
            new Borders(88, 962, 5901),
            new Borders(83, 905, 6586),
            new Borders(112, 912, 6148),
            new Borders(109, 1170, 6884),
            new Borders(133, 813, 6428)
    };

    Borders [] front20 = new Borders[]{
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(5, 17, 66),
            new Borders(5, 35, 154),
            new Borders(6, 57, 328),
            new Borders(10, 66, 572),
            new Borders(10, 71, 964),
            new Borders(10, 53, 1135),
            new Borders(13, 83, 1609),
            new Borders(20, 66, 1708),
            new Borders(21, 68, 2076),
            new Borders(21, 87, 2112),
            new Borders(22, 196, 2720),
            new Borders(22, 363, 2794),
            new Borders(23, 259, 3517),
            new Borders(23, 247, 3248),
            new Borders(25, 216, 3836),
            new Borders(25, 465, 3952),
            new Borders(26, 489, 4246),
            new Borders(29, 432, 4676),
            new Borders(39, 560, 4602),
            new Borders(45, 542, 4514),
            new Borders(37, 604, 5426),
            new Borders(44, 548, 5332),
            new Borders(45, 774, 5577),
            new Borders(55, 1148, 6156),
            new Borders(52, 573, 6441),
            new Borders(56, 978, 6408),
            new Borders(101, 1023, 6582),
            new Borders(101, 762, 6685)
    };

    Borders [] front50 = new Borders[]{
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(0, 0, 0),
            new Borders(5, 32, 135),
            new Borders(5, 68, 211),
            new Borders(6, 66, 403),
            new Borders(9, 73, 659),
            new Borders(10, 104, 1041),
            new Borders(10, 101, 1323),
            new Borders(15, 85, 1672),
            new Borders(20, 85, 1887),
            new Borders(21, 87, 2255),
            new Borders(22, 84, 2594),
            new Borders(24, 83, 2578),
            new Borders(40, 190, 2737),
            new Borders(42, 152, 3434),
            new Borders(45, 183, 3512),
            new Borders(48, 321, 4138),
            new Borders(51, 233, 4089),
            new Borders(52, 263, 4259),
            new Borders(53, 253, 4581),
            new Borders(52, 591, 5578),
            new Borders(53, 643, 5196),
            new Borders(53, 217, 5525),
            new Borders(56, 297, 5418),
            new Borders(55, 713, 6199),
            new Borders(56, 935, 5392),
            new Borders(58, 867, 6425),
            new Borders(57, 781, 6049),
            new Borders(59, 555, 6869),
            new Borders(59, 1184, 6815)
    };

    
//    public static boolean getChangePoint(int m) {
//        if(numFront == 0) {
//
//        } else if (numFront)
//    }


    class Borders {
        int left, right, vertex;

        Borders(int mi, int v, int ma) {
            left = mi;
            vertex = v;
            right = ma;
        }

    }

}
