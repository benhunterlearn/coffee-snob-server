package com.swf.coffeesnobserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShopsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShopRepository repository;

    private Shop groovyShop;
    private Shop weirdShop;


    private ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @BeforeEach
    void setUp() {
        this.groovyShop = new Shop()
                .setName("Hip Sips")
                .setAddress("Austin, TX")
                .setWebsite("hipsips.coffee");
        groovyShop = this.repository.save(groovyShop);
        this.weirdShop = new Shop()
                .setName("Cool Beans")
                .setAddress("Austin, TX")
                .setWebsite("holeinthewall.coffee");
        weirdShop = this.repository.save(weirdShop);
    }

    @Test
    public void getAllShops() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shop")
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(groovyShop.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(groovyShop.getName())))
                .andExpect(jsonPath("$[0].address", is(groovyShop.getAddress())))
                .andExpect(jsonPath("$[0].website", is(groovyShop.getWebsite())))
                .andExpect(jsonPath("$[1].id", is(weirdShop.getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(weirdShop.getName())))
                .andExpect(jsonPath("$[1].address", is(weirdShop.getAddress())))
                .andExpect(jsonPath("$[1].website", is(weirdShop.getWebsite())));
    }

    @Test
    public void getShopById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/shop/" + groovyShop.getId())
                .accept(MediaType.APPLICATION_JSON);
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(groovyShop.getId().intValue())))
                .andExpect(jsonPath("$.name", is(groovyShop.getName())))
                .andExpect(jsonPath("$.address", is(groovyShop.getAddress())))
                .andExpect(jsonPath("$.website", is(groovyShop.getWebsite())));
    }

    @Test
    public void postCreateNewShop() throws Exception {
        Shop trendyShop = new Shop()
                .setName("Smells Good Coffee")
                .setAddress("South Austin, TX")
                .setWebsite("smellsgood.coffee");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/shop/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(trendyShop));
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is(trendyShop.getName())))
                .andExpect(jsonPath("$.address", is(trendyShop.getAddress())))
                .andExpect(jsonPath("$.website", is(trendyShop.getWebsite())));
    }

    @Test
    public void patchUpdatesShopWithPartialInformation() throws Exception {
        String newName = "Cooler Coffee Winter Flavors";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/shop/" + groovyShop.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + newName + "\"}");
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(groovyShop.getId().intValue())))
                .andExpect(jsonPath("$.name", is(newName)))
                .andExpect(jsonPath("$.address", is(groovyShop.getAddress())))
                .andExpect(jsonPath("$.website", is(groovyShop.getWebsite())));

        String newAddress = "We moved to East Austin.";
        requestBuilder = MockMvcRequestBuilders.patch("/shop/" + groovyShop.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"" + newAddress + "\"}");
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(groovyShop.getId().intValue())))
                .andExpect(jsonPath("$.name", is(newName)))
                .andExpect(jsonPath("$.address", is(newAddress)))
                .andExpect(jsonPath("$.website", is(groovyShop.getWebsite())));

        String newWebsite = "cool.coffee";
        requestBuilder = MockMvcRequestBuilders.patch("/shop/" + groovyShop.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"website\":\"" + newWebsite + "\"}");
        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(groovyShop.getId().intValue())))
                .andExpect(jsonPath("$.name", is(newName)))
                .andExpect(jsonPath("$.address", is(newAddress)))
                .andExpect(jsonPath("$.website", is(newWebsite)));
    }

    @Test
    public void deleteRemovesShopFromRepository() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/shop/" + groovyShop.getId());
        mvc.perform(requestBuilder)
                .andExpect(status().isOk());
        assertFalse(repository.findById(groovyShop.getId()).isPresent());
    }
}