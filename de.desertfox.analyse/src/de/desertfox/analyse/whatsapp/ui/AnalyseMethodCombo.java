package de.desertfox.analyse.whatsapp.ui;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import de.desertfox.analyse.whatsapp.model.AnalyseMethod;

public class AnalyseMethodCombo extends Combo {

	private AnalyseMethod[] methods;

	public AnalyseMethodCombo(Composite parent, int style) {
		super(parent, style);
		fill();
	}

	private void fill() {
		methods = AnalyseMethod.values();
		String[] items = new String[methods.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = methods[i].getDisplayName();
		}
		setItems(items);
	}

	@Override
	public String getText() {
		int selectionIndex = getSelectionIndex();
		return methods[selectionIndex].getDisplayName();
	}

	public AnalyseMethod getSelectedItem() {
		int selectionIndex = getSelectionIndex();
		return methods[selectionIndex];
	}

	@Override
	protected void checkSubclass() {
	}
	
}
