package prs.db.lineitem;

import prs.business.LineItem;

public interface LineItemWriter {

	boolean addLineItem(LineItem inLineItem);
}
