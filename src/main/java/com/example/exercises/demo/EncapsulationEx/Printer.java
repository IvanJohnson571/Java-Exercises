package com.example.exercises.demo.EncapsulationEx;

import com.example.exercises.demo.CompositionEx.SmartKitchen;
import org.springframework.stereotype.Component;

@Component
public class Printer {

    private int tonerLevel;
    private int pagesPrinted;
    private boolean duplex;

    public Printer() {
    }

    public Printer( int tonerLevel, boolean duplex) {
        this.pagesPrinted = 0;
        this.tonerLevel = (tonerLevel >= 0 && tonerLevel <= 100) ? tonerLevel : -1;
        this.duplex = duplex;
    }

    public int addToner(int tonerAmount) {

        int tempAmount = tonerAmount + tonerLevel;

        if (tempAmount > 100 || tempAmount < 0) {
            return -1;
        }

        tonerLevel += tonerAmount;
        return tonerLevel;

    }

    public int printPages(int pages) {

        int jobPages = (duplex) ? (pages / 2) + (pages % 2) : pages;
        pagesPrinted += jobPages;
        return jobPages;

    }

    public int getPagesPrinted() {
        return pagesPrinted;
    }

    public static void exercises() {

       Printer printer = new Printer(50, true);
       System.out.println("initial page count = " + printer.getPagesPrinted());

       int pagesPrinted = printer.printPages(5);
        System.out.printf("Current job pages: %d, Printer Total: %d %n",
                pagesPrinted,printer.getPagesPrinted());

        pagesPrinted = printer.printPages(10);
        System.out.printf("Current job pages: %d, Printer Total: %d %n",
                pagesPrinted,printer.getPagesPrinted());
    }

}
