package org.plast.reg.ui;

import org.plast.reg.DummyTableModel;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
public class DummyTable extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label label_1;
	@AutoGenerated
	private Table table_1;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public DummyTable() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		DummyTableModel dtm = new DummyTableModel();
		SQLContainer table1Container = dtm.GetTableQuerySQLContainer("dt_dummy");
		table_1.setContainerDataSource(table1Container);
		
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// table_1
		table_1 = new Table();
		table_1.setImmediate(false);
		table_1.setWidth("100.0%");
		table_1.setHeight("360px");
		mainLayout
				.addComponent(table_1, "top:18.0px;right:265.0px;left:0.0px;");
		
		// label_1
		label_1 = new Label();
		label_1.setImmediate(false);
		label_1.setWidth("100.0%");
		label_1.setHeight("-1px");
		label_1.setValue("Dummy Absolute Table Layout");
		mainLayout.addComponent(label_1, "top:0.0px;left:0.0px;");
		
		return mainLayout;
	}

}
