/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content.source.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author frede
 */
public class Filter {
    private List<WeightedFilter> filters;
    
    public Filter(WeightedFilter[] filtersArr){
        this.filters = Arrays.asList(filtersArr);
    }
    public int getWeight(String text){
        int weight = 0;
        for(WeightedFilter filter : filters){
            weight += filter.getWeight(text);
        }
        return weight;
    }
}
