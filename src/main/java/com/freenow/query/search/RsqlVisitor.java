package com.freenow.query.search;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class RsqlVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

	 private GenericRsqlSpecBuilder<T> builder;
	
	 
	 public RsqlVisitor()
	 {
	        builder = new GenericRsqlSpecBuilder<T>();
	 }
	 
	 
	
	@Override
	public Specification<T> visit(AndNode node, Void param) {
		// TODO Auto-generated method stub
		return builder.createSpecification(node);
	}

	@Override
	public Specification<T> visit(OrNode node, Void param) {
		// TODO Auto-generated method stub
		 return builder.createSpecification(node);
	}

	@Override
	public Specification<T> visit(ComparisonNode node, Void param) {
		// TODO Auto-generated method stub
		 return builder.createSpecification(node);
	}

}
