package com.ooad.secondchance.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Priyanka on 4/2/21.
 */
public class ServiceUtils {

    public static void copyNonNullProperties(Object src, Object target, String... extra) {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.stream(getNullPropertyNames(src)).distinct().collect(Collectors.toList()));
        for(String str : extra) {
            list.add(str);
        }
        BeanUtils.copyProperties(src, target, list.toArray(String[]::new));
    }

    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
