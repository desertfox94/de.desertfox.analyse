package de.desertfox.analyse.whatsapp.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.desertfox.analyse.whatsapp.core.Analyser;

public class WhatsappAnalyserAppWindow {

	protected Shell shell;
	private Text sourcePathText;
	private Text targetPathText;
	private AnalyseMethodCombo methodCombo;
	private Analyser analyser = new Analyser();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WhatsappAnalyserAppWindow window = new WhatsappAnalyserAppWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(3, false));

		Label lblQuelle = new Label(shell, SWT.NONE);
		lblQuelle.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblQuelle.setText("Quelle");

		sourcePathText = new Text(shell, SWT.BORDER);
		sourcePathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button selectInputFile = new Button(shell, SWT.NONE);
		selectInputFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell);
				dialog.setFilterExtensions(new String[] { "*.txt" });
				String source = dialog.open();
				if (source == null || (source = source.trim()).isEmpty()) {
					return;
				}
				File file = new File(source);
				if (file.exists() && file.isFile()) {
					sourcePathText.setText(source);
				}
			}
		});
		selectInputFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		selectInputFile.setText("Datei ausw\u00E4hlen");

		Label lblZiel = new Label(shell, SWT.NONE);
		lblZiel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblZiel.setText("Ziel");

		targetPathText = new Text(shell, SWT.BORDER);
		targetPathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button selectTarget = new Button(shell, SWT.NONE);
		selectTarget.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		selectTarget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell);
				String target = dialog.open();
				if (target == null || (target = target.trim()).isEmpty()) {
					return;
				}
				File file = new File(target);
				if (file.exists() && file.isDirectory()) {
					targetPathText.setText(target);
				}
			}
		});
		selectTarget.setText("Verzeichnis w\u00E4hlen");
		new Label(shell, SWT.NONE);

		Label lblExportWhlen = new Label(shell, SWT.NONE);
		lblExportWhlen.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblExportWhlen.setText("Export w\u00E4hlen");

		methodCombo = new AnalyseMethodCombo(shell, SWT.NONE);
		methodCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(shell, SWT.NONE);

		// Label lblArtDerVisualisierung = new Label(shell, SWT.NONE);
		// lblArtDerVisualisierung.setLayoutData(new GridData(SWT.RIGHT,
		// SWT.CENTER, false, false, 1, 1));
		// lblArtDerVisualisierung.setText("Art der Visualisierung");

		// Combo combo = new Combo(shell, SWT.NONE);
		// combo.setItems(new String[] {"Excel", "Text"});
		// combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
		// 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		Button analyseButton = new Button(shell, SWT.NONE);
		analyseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex = methodCombo.getSelectionIndex();
				if (selectionIndex == -1) {
					return;
				}
				String item = methodCombo.getItem(selectionIndex);
				if (item != null && (item = item.trim()).isEmpty()) {
					return;
				}
				boolean couldInitialize = analyser.init(sourcePathText.getText(), targetPathText.getText());
				if (!couldInitialize) {
					return;
				}
				analyser.analyse(methodCombo.getSelectedItem());
			}
		});
		analyseButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		analyseButton.setText("Analysieren");

	}
}