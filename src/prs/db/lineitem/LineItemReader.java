package prs.db.lineitem;

import java.util.ArrayList;

import prs.business.LineItem;

public interface LineItemReader {

	ArrayList<LineItem> getLineItemsByRequestID(int inRequestID);
}
