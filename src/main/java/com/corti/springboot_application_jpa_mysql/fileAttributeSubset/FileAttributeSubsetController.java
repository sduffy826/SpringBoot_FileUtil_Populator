package com.corti.springboot_application_jpa_mysql.fileAttributeSubset;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockSymbolController {
  
  @Autowired
  private StockSymbolService stockSymbolService;

  @RequestMapping("/symbols")
  public List<StockSymbol> getAllStockSymbols() {
    return stockSymbolService.getAllStockSymbols();
  }
  
  @RequestMapping("/symbols/{ticker}")
  public StockSymbol getStockSymbol(@PathVariable("ticker") String ticker) {
    return stockSymbolService.getStockSymbol(ticker);
  }
  
 
  @RequestMapping(method=RequestMethod.POST, value="/symbols")  // POST request to insert a new topic
  public void addStockSymbol(@RequestBody StockSymbol stockSymbol) {  // This says take the request body and create a topic instance
    // Get the post body, convert to topic and all service
    stockSymbolService.addStockSymbol(stockSymbol);
  }
  
  @RequestMapping(method=RequestMethod.PUT, value="/symbols")  // PUT for updates
  public void updateStockSymbol(@RequestBody StockSymbol stockSymbol) {  
    stockSymbolService.updateStockSymbol(stockSymbol);
  }
  
  
  @RequestMapping(method=RequestMethod.DELETE, value="/symbols/{ticker}")  // DELETE requests, they just need to give ticker 
  public void deleteTopic(@PathVariable String ticker) {
    stockSymbolService.deleteStockSymbol(ticker);
  }
}  
*/  