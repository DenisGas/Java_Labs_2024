package org.example;


import com.github.javafaker.Faker;

import java.util.stream.IntStream;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        var faker = new Faker();
        OrderProcessor<Electronics> electronicsOrder = new OrderProcessor<>(new Electronics(faker.pokemon().name(),faker.pokemon().hashCode(), faker.pokemon().location()));

        var clothingList  = IntStream.range(0, 1000).mapToObj(i -> Clothing.builder()
                        .name(faker.commerce().productName())
                        .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                        .description(faker.lebowski().quote())
                        .build())
                .toList();
        clothingList.parallelStream()
                .map(OrderProcessor::new)
                .forEach(OrderProcessor::startProcessing);

//
////        electronicsOrder.process();
//        var clothing = Clothing.builder()
//                .name(faker.pokemon().name())
//                .price((double) faker.pokemon().hashCode())
//                .description(faker.lebowski().quote())
//                .build();
////
        clothingList.stream().map(OrderProcessor::new).forEach(OrderProcessor::process);
}}