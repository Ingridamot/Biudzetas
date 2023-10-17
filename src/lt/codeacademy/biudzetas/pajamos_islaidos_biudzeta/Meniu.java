package lt.codeacademy.biudzetas.pajamos_islaidos_biudzeta;

public class Meniu {

    public String pagrindinisMeniu() {
        return """
                Pasirinkite, kokį veiksmą norite atlikti:
                [1] - ĮVESTI NAUJAS PAJAMAS.
                [2] - ĮVESTI NAUJAS IŠLAIDAS.
                [3] - KOREGUOTI ĮVESTĮ.
                [4] - PAŠALINTI ĮRAŠĄ.
                [5] - ATSPAUSDINTI PAJAMŲ IR IŠLAIDŲ IŠKLOTINĘ.
                [6] - ATSPAUSDINTI BALANSA.
                [0] - BAIGTI.""";
    }


    public String pajamuKategorijuMeniu() {
        return"""
                Pasirinkite kokios kategorijos pajamas norite įvesti:\s
                [1] - ALGOS pajamos.
                [2] - INVESTICIJŲ pajamos.
                [3] - STIPENDIJOS pajamos.
                [4] - Grįžti į pradinį meniu.""";
    }

    public String islaiduKategorijuMeniu() {
        return """
                Pasirinkite kokios kategorijos išlaidas norite įvesti:\s
                [1] - PASKOLOS išlaidos.
                [2] - LIZINGO išlaidos.
                [3] - KELIONĖS išlaidos.
                [4] - PIRKINIŲ išlaidos.
                [5] - Grįžti į pradinį meniu.""";
    }

}
