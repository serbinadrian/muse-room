package com.koley.musrights.services;

import com.koley.musrights.domains.Composition;
import com.koley.musrights.domains.Genres;
import com.koley.musrights.domains.User;
import com.koley.musrights.repositories.CompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchAndFiltersService {
    @Autowired
    CompositionRepository compositionRepository;
    List<Composition> data;
    String search;
    String sort;
    List<Genres> filters;
    User user;
    boolean newParamValue = false;
    boolean isSet = false;

    public List<Composition> getData() {
        return data;
    }

    public void insertParams(User user, String search, String sort, Genres[] filters) {

        System.out.println("search query: " + search);
        System.out.println("sort query: " + sort);
        System.out.println("filters query: " + Arrays.toString(filters));

        this.search = Objects.requireNonNullElse(search, "");
        this.sort = Objects.requireNonNullElse(sort, "sortRentDesc");
        if (filters != null) {
            this.filters = new ArrayList<>(List.of(filters));
        } else {
            this.filters = new ArrayList<>();
        }
        this.user =  user;
        this.newParamValue = true;
    }

    public boolean isChanged(String search, String sort, Genres[] filters, User user) {
        String currentSearch = Objects.requireNonNullElse(search, "");
        String currentSort = Objects.requireNonNullElse(sort, "sortRateDesc");
        List<Genres> currentFilters = new ArrayList<>();
        if (filters != null) {
            currentFilters = new ArrayList<>(List.of(filters));
        }

        return !Objects.equals(this.search, currentSearch) || !Objects.equals(this.sort, currentSort) || this.filters != currentFilters || !Objects.equals(this.user, user);
    }

    public void build() {
        this.data = new ArrayList<>();
        List<Composition> data = switch (this.sort) {
            case "sortRentDesc" -> compositionRepository.findAll(Sort.by(Sort.Direction.DESC, "rentTimes"));
            case "sortRentAsc" -> compositionRepository.findAll(Sort.by(Sort.Direction.ASC, "rentTimes"));
            default -> new ArrayList<>();
        };

        System.out.println("found rows: " + data.size());
        int size = this.filters.size();
        if (size != 0 || !this.search.equals("")) {
            for (Composition composition : data) {
                boolean searchPassed = composition.getName().toLowerCase().contains(this.search.toLowerCase())
                        ||
                        composition.getAuthor().toLowerCase().contains(this.search.toLowerCase());
                boolean filterPassed = calculateFilterPassed(composition);

                if (searchPassed && filterPassed && composition.isAvailableToBuy()) {
                    this.data.add(composition);
                }
            }
        } else {
            for (Composition composition : data) {
                if (composition.isAvailableToBuy()) {
                    this.data.add(composition);
                }
            }
        }
        this.isSet = true;
    }


    public String getSearch() {
        return this.search;
    }

    public List<Integer> getSort() {
        List<Integer> sort = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            sort.add(0);
        }
        switch (this.sort) {
            case "sortRentDesc" -> sort.set(0, 1);
            case "sortRentAsc" -> sort.set(1, 1);
        }
        return sort;
    }

    public List<Integer> getFilters() {
        List<Integer> filters = new ArrayList<>();
        List<Genres> genres = new ArrayList<>(EnumSet.allOf(Genres.class));
        int size = this.filters.size();
        int globalGenresSize = genres.size();
        for (int i = 0; i < globalGenresSize; i++) {
            filters.add(i, 0);
        }
        if (size != 0) {
            for (Genres filter : this.filters) {
                if (genres.contains(filter)) {
                    int index = genres.indexOf(filter);
                    filters.set(index, 1);
                }
            }
        }
        return filters;
    }

    private boolean calculateFilterPassed(Composition composition) {
        if (this.filters.isEmpty()){
            return true;
        }
        else return this.filters.contains(composition.getGenre());
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }
}
