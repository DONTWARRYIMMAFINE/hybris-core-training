package de.hybris.training.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.training.daos.NewsDAO;
import de.hybris.training.model.NewsModel;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component(value = "newsDAO")
public class DefaultNewsDAO implements NewsDAO
{
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    private final FlexibleSearchService flexibleSearchService;

    public DefaultNewsDAO(FlexibleSearchService flexibleSearchService)
    {
        this.flexibleSearchService = flexibleSearchService;
    }

    @Override
    public List<NewsModel> getNewsOfTheDay(final Date date)
    {
        if (date == null)
        {
            return Collections.emptyList();
        }

        final String theDay = new SimpleDateFormat(SQL_DATE_FORMAT).format(date);
        final String theNextDay = new SimpleDateFormat(SQL_DATE_FORMAT).format(oneDayAfter(date));
        final String queryString = //
                "SELECT {p:" + NewsModel.PK + "} "//
                        + "FROM {" + NewsModel._TYPECODE + " AS p} " //
                        + "WHERE {date} >= DATE \'" + theDay + "\' "//
                        + "AND {date} <= DATE \'" + theNextDay + "\'";

        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);

        return flexibleSearchService.<NewsModel> search(query).getResult();
    }

    private Date oneDayAfter(final Date date)
    {
        final Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.DATE, 1);

        return cal.getTime();
    }
}