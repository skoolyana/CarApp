package com.freenow.service;

import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.freenow.domainobject.DriverDO;
import com.freenow.query.search.RsqlVisitor;
import com.freenow.service.driver.DriverService;
import static org.junit.Assert.assertThat;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTest {
	
	 @Autowired
	 DriverService driverService;
	 
	 
	 
	 @Test
	    public void givenOnlineStatus_whenSearched_thenMatch()
	    {
	        //given
	        String searchExpression = "onlineStatus==ONLINE";
	        Node rootNode = new RSQLParser().parse(searchExpression);
	        Specification<DriverDO> spec = rootNode.accept(new RsqlVisitor<DriverDO>());
	        //search
	        List<DriverDO> searchResults = driverService.findAll(spec);
	        
	        //Result
	        assertThat( searchResults, hasSize(4));
	    }
	    
	 
	 
	 @Test
	    public void givenCarAttributes_whenSearched_thenMatch()
	    {
	        //given license Plate AND rating Greater Than
	        String searchExpression = "licensePlate==111-44-s;rating=gt=2";
	        Node rootNode = new RSQLParser().parse(searchExpression);
	        Specification<DriverDO> spec = rootNode.accept(new RsqlVisitor<DriverDO>());
	        //search
	        List<DriverDO> searchResults = driverService.findAll(spec);
	        
	        //Result
	        assertThat( searchResults, hasSize(1));
	               
	    }
	 
	    

}
