package mypack.BusinessLayer;

import java.io.Serializable;

public class Date implements Serializable {
    private int day, mounth, year;
    private int hour;

    public int getDay() {
        return day;
    }

    public int getMounth() {
        return mounth;
    }

    @Override
    public String toString() {
        return "Date{" +
                "day=" + day +
                ", mounth=" + mounth +
                ", year=" + year +
                ", hour=" + hour +
                '}';
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }


    public Date(int day, int mounth, int year, int hour) {
        int val = 30;
        if(year>5000)
            year%=5000;
        if(hour>24)
            hour%=24;
        this.hour=hour;

        if (mounth == 1 || mounth == 3 || mounth == 5 || mounth == 7 || mounth == 8 || mounth == 10 || mounth == 12)
            val++;
        else if (mounth == 2) {
            if (year % 400 == 0)
                val = 29;
            else val = 28;
        }
        if (day > val)
            this.day = day % val;
        else this.day = day;
        if (mounth > 12)
            this.mounth = mounth % 12 + 1;
        else this.mounth = mounth;
        this.year = year;
    }
}
