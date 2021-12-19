package com.example.thankdiary;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<CalendarDay> dates;

    private final Drawable drawable;

    public EventDecorator(int color, Collection<CalendarDay> dates, Activity context){
        drawable = context.getResources().getDrawable(R.drawable.more);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new DotSpan(5, color));
    }
}
