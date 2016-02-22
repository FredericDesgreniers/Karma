/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.winterdev.server.content.source.filter;

/**
 *
 * @author frede
 */
public class WeightedFilter {
    private int weight;
    private String keyword;
    public WeightedFilter(String keyword, int weight){
        this.keyword = keyword.toLowerCase();
        this.weight = weight;
    }
    
    public int getWeight(String text){
        if(text.contains(keyword)){
            return weight;
        }
        return 0;
    }
}
