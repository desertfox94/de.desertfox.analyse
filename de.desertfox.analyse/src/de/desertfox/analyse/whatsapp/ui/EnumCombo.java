package de.desertfox.analyse.whatsapp.ui;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import de.desertfox.analyse.whatsapp.model.IComboViewableEnum;

public class EnumCombo extends Combo {

	private IComboViewableEnum[] enums;

	public EnumCombo(Composite parent, int style, IComboViewableEnum[] enums) {
		super(parent, style);
		this.enums = enums;
		fill();
	}

	private void fill() {
		String[] items = new String[enums.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = enums[i].getDisplayName();
		}
		setItems(items);
	}

	@Override
	public String getText() {
		int selectionIndex = getSelectionIndex();
		return enums[selectionIndex].getDisplayName();
	}

	public IComboViewableEnum getSelectedItem() {
		int selectionIndex = getSelectionIndex();
		return enums[selectionIndex];
	}

	@Override
	protected void checkSubclass() {
	}
	
}
