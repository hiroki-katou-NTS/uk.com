/*!@license
* Infragistics.Web.ClientUI Grid localization resources 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/

/*global jQuery */
(function ($) {
    $.ig = $.ig || {};

    if (!$.ig.Grid) {
	    $.ig.Grid = {};

	    $.extend($.ig.Grid, {

		    locale: {
		        noSuchWidget: "{featureName} は認識されませんでした。その機能が存在し、スペルミスがないことを確認してください。",
		        autoGenerateColumnsNoRecords: "autoGenerateColumns は有効ですが、データ ソースにレコードがありません。列を決定するには、レコードを持つデータ ソースを読み込んでください。",
		        optionChangeNotSupported: "{optionName} は初期化後に編集できません。値は初期化後に設定してください。 ",
		        optionChangeNotScrollingGrid: "{optionName} を初期化後に編集できません。グリッドが初めにスクロールしないため、完全に再描画する必要があります。このオプションを初期化で設定してください。",
		        widthChangeFromPixelsToPercentagesNotSupported: "グリッドの width オプションをピクセルからパーセンテージへ動的に変更できません。",
		        widthChangeFromPercentagesToPixelsNotSupported: "グリッドの width オプションをパーセンテージからピクセルへ動的に変更できません。",
		        noPrimaryKeyDefined: "グリッドに primaryKey が定義されていません。GridEditing などの機能を使用するには、primaryKey を構成してください。",
		        indexOutOfRange: "指定した行インデックスが範囲外です。{0} および {max} の間の行インデックスを提供してください。",
		        noSuchColumnDefined: "指定した列キーは有効ではありません。定義されたグリッド列のキーと一致する列キーを提供してください。",
		        columnIndexOutOfRange: "指定した列インデックスが範囲外です。{0} および {max} の間の列インデックスを入力してください。",
		        recordNotFound: "ID {id} のレコードがデータ ビューで見つかりませんでした。検索で ID が使用されることを確認し、必要に応じて変更してください。",
		        columnNotFound: "キー {key} の列が見つかりませんでした。検索でキーが使用されることを確認し、必要に応じて変更してください。",
			    colPrefix: "列 ",
			    columnVirtualizationRequiresWidth: "仮想化および columnVirtualization にグリッドの幅または列の幅を設定してください。グリッドの幅、defaultColumnWidth、または各列の幅に値を入力してください。",
			    virtualizationRequiresHeight: "仮想化のグリッドの高さを設定してください。グリッドの高さに値を入力してください。",
			    colVirtualizationDenied: "columnVirtualization に異なる virtualizationMode 設定を使用してください。virtualizationMode は 'fixed' に設定してください。",
			    noColumnsButAutoGenerateTrue: "autoGenerateColumns は無効で、グリッドに列が定義されていません。autoGenerateColumns を有効にするか、手動的に列を指定してください。",
			    noPrimaryKey: "igHierarchicalGrid に primaryKey を定義してください。グリッドの primaryKey オプションを構成してください。",
			    templatingEnabledButNoTemplate: "jQueryTemplating の有効化は、テンプレートを定義してください。テンプレートは rowTemplate オプションで設定してください。",
			    expandTooltip: "行の展開",
			    collapseTooltip: "行の縮小",
			    featureChooserTooltip: "機能セレクター",
			    movingNotAllowedOrIncompatible: "指定した列は移動できません。列が存在し、その終了位置が列のレイアウトを崩さないかを確認してください。",
			    allColumnsHiddenOnInitialization: "すべての列を初期化で非表示にすることはできません。少なくとも 1 列は表示するよう設定してしてください。",
			    virtualizationNotSupportedWithAutoSizeCols: "仮想化には '*' 以外の列幅を設定してください。列幅をピクセル単位の数値で設定してください。",
			    columnVirtualizationNotSupportedWithPercentageWidth: "仮想化には '*' 以外の列幅を設定してください。列幅をピクセル単位の数値で設定してください。",
			    mixedWidthsNotSupported: "すべての列の幅は同じ方法で設定します。すべての列幅をパーセンテージまたはピクセル数で設定します。",
			    multiRowLayoutColumnError: "{key1} のキーを持つ列を複数行レイアウトに追加できません。レイアウトの指定した位置に {key2} のキーを持つ列は配置されています。",
			    multiRowLayoutNotComplete: "複数行レイアウトは完了ではありません。列定義は、空スペースを持つレイアウトを作成するため、正しく描画できません。",
			    multiRowLayoutMixedWidths: "混合幅 (パーセンテージおよびピクセル単位) は複数行レイアウトでサポートされていません。すべての列幅をピクセル単位またはパーセンテージで定義してください。 ",
			    scrollableGridAreaNotVisible: "固定ヘッダーおよび固定フッター領域は利用可能なグリッドの高さより大きくなります。スクロール可能な領域が表示されていません。グリッドの高さをより大きく設定してください。"
		    }
	    });

	    $.ig.GridFiltering = $.ig.GridFiltering || {};

	    $.extend($.ig.GridFiltering, {

		    locale: {
			    startsWithNullText: "～で始まる",
			    endsWithNullText: "～で終わる",
			    containsNullText: "～を含む",
			    doesNotContainNullText: "～を含まない",
			    equalsNullText: "～に等しい",
			    doesNotEqualNullText: "～に等しくない",
			    greaterThanNullText: "～より大きい",
			    lessThanNullText: "～より小さい",
			    greaterThanOrEqualToNullText: "以上",
			    lessThanOrEqualToNullText: "以下",
			    onNullText: "指定日",
			    notOnNullText: "指定日以外",
			    afterNullText: "～の後",
			    beforeNullText: "～の前",
			    emptyNullText: "空",
			    notEmptyNullText: "空以外",
			    nullNullText: "null 値",
			    notNullNullText: "null 値以外",
			    startsWithLabel: "～で始まる",
			    endsWithLabel: "～で終わる",
			    containsLabel: "～を含む",
			    doesNotContainLabel: "～を含まない",
			    equalsLabel: "～に等しい",
			    doesNotEqualLabel: "～に等しくない",
			    greaterThanLabel: "～より大きい",
			    lessThanLabel: "～より小さい",
			    greaterThanOrEqualToLabel: "以上",
			    lessThanOrEqualToLabel: "以下",
			    trueLabel: "True",
			    falseLabel: "False",
			    afterLabel: "～の後",
			    beforeLabel: "～の前",
			    todayLabel: "今日",
			    yesterdayLabel: "昨日",
			    thisMonthLabel: "今月",
			    lastMonthLabel: "先月",
			    nextMonthLabel: "翌月",
			    thisYearLabel: "今年",
			    lastYearLabel: "昨年",
			    nextYearLabel: "来年",
			    clearLabel: "フィルターをクリア",
			    noFilterLabel: "なし",
			    onLabel: "指定日",
			    notOnLabel: "指定日以外",
			    advancedButtonLabel: "詳細",
			    filterDialogCaptionLabel: "高度なフィルター",
			    filterDialogConditionLabel1: "以下の条件の",
			    filterDialogConditionLabel2: "と一致するレコードを表示",
			    filterDialogConditionDropDownLabel: "フィルター条件",
			    filterDialogOkLabel: "OK",
			    filterDialogCancelLabel: "キャンセル",
			    filterDialogAnyLabel: "いずれか",
			    filterDialogAllLabel: "すべて",
			    filterDialogAddLabel: "追加",
			    filterDialogErrorLabel: "サポートされるフィルター数の最大値に達しました。",
			    filterDialogCloseLabel: "フィルター ダイアログを閉じる",
			    filterSummaryTitleLabel: "検索結果",
			    filterSummaryTemplate: "一致するレコード: ${matches}",
			    filterDialogClearAllLabel: "すべてクリア",
			    tooltipTemplate: "適用済みのフィルター: ${condition}",
			    featureChooserText: "フィルターの非表示",
			    featureChooserTextHide: "フィルターの表示",
			    featureChooserTextAdvancedFilter: "フィルター",
			    virtualizationSimpleFilteringNotAllowed: "ColumnVirtualization には他のフィルター タイプを設定してください。フィルター モードを 'advanced' に設定、または advancedModeEditorsVisible を無効にしてください。",
			    multiRowLayoutSimpleFilteringNotAllowed: "複数行レイアウトには他のフィルター タイプを設定してください。フィルター モードを 'advanced' に設定してください。",
			    featureChooserNotReferenced: "FeatureChooser への参照がありません。プロジェクトに infragistics.ui.grid.featurechooser.js を含めるか、ローダーまたは結合スクリプト ファイルを使用してください。",
			    conditionListLengthCannotBeZero: "columnSettings の conditionList 配列が空です。conditionList に適切な配列を使用してください。",
			    conditionNotValidForColumnType: "条件 '{0}' は現在の構成に有効ではありません。{1} 列タイプに適切な条件で置き換えてください。",
			    defaultConditionContainsInvalidCondition: "'{0}' 列の defaultExpression に無効な条件が含まれています。{0} 列タイプに適切な条件で置き換えてください。"
		    }
	    });

	    $.ig.GridGroupBy = $.ig.GridGroupBy || {};

	    $.extend($.ig.GridGroupBy, {

		    locale: {
			    emptyGroupByAreaContent: "グループ化するには、列をここへドラッグするか、{0}します。",
			    emptyGroupByAreaContentSelectColumns: "列を選択",
			    emptyGroupByAreaContentSelectColumnsCaption: "列を選択",
			    expandTooltip: "グループ化された行を展開する",
			    collapseTooltip: "グループ化された行を縮小する",
			    removeButtonTooltip: "列のグループ化を解除する",
			    modalDialogCaptionButtonDesc: "昇順に並べ替え",
			    modalDialogCaptionButtonAsc: "降順に並べ替え",
			    modalDialogCaptionButtonUngroup: "グループ化解除",
			    modalDialogGroupByButtonText: "グループ化",
			    modalDialogCaptionText: 'グループ化に追加する',
			    modalDialogDropDownLabel: '表示: ',
			    modalDialogClearAllButtonLabel: 'すべてクリア',
			    modalDialogRootLevelHierarchicalGrid: 'ルート',
			    modalDialogDropDownButtonCaption: "表示／非表示",
			    modalDialogButtonApplyText: '適用',
			    modalDialogButtonCancelText: 'キャンセル',
			    fixedVirualizationNotSupported: 'GroupBy に他の仮想化設定を使用してください。virtualizationMode は "continuous" に設定してください。',
			    summaryRowTitle: 'グループ化された集計行'
		    }
	    });

	    $.ig.GridHiding = $.ig.GridHiding || {};

	    $.extend($.ig.GridHiding, {
		    locale: {
			    columnChooserDisplayText: "列の選択",
			    hiddenColumnIndicatorTooltipText: "非表示列",
			    columnHideText: "非表示",
			    columnChooserCaptionLabel: "列の選択",
			    columnChooserCheckboxesHeader: "ビュー",
			    columnChooserColumnsHeader: "列",
			    columnChooserCloseButtonTooltip: "閉じる",
			    hideColumnIconTooltip: "非表示",
			    featureChooserNotReferenced: "FeatureChooser への参照がありません。プロジェクトに infragistics.ui.grid.featurechooser.js を含めるか、結合スクリプト ファイルのいずれかを使用してください。",
			    columnChooserShowText: "表示",
			    columnChooserHideText: "非表示",
			    columnChooserResetButtonLabel: "リセット",
			    columnChooserButtonApplyText: '適用',
			    columnChooserButtonCancelText: 'キャンセル'
		    }
	    });

		$.ig.GridResizing = $.ig.GridResizing || {};

		$.extend($.ig.GridResizing, {
			locale: {
			    noSuchVisibleColumn: "指定したキーの表示列はありません。showColumn() メソッドは、サイズ変更する前の列に使用してください。",
			    resizingAndFixedVirtualizationNotSupported: "列のサイズ変更に異なる仮想化設定が必要です。rowVirtualization を使用し、virtualizationMode を continuous に設定してください。 "
			}
		});

	    $.ig.GridPaging = $.ig.GridPaging || {};

	    $.extend($.ig.GridPaging, {

		    locale: {
			    pageSizeDropDownLabel: "表示: ",
			    pageSizeDropDownTrailingLabel: "レコード",
			    //pageSizeDropDownTemplate: "${dropdown} レコードの表示",
			    nextPageLabelText: "次へ",
			    prevPageLabelText: "前へ",
			    firstPageLabelText: "",
			    lastPageLabelText: "",
			    currentPageDropDownLeadingLabel: "ページ",
			    currentPageDropDownTrailingLabel: " / ${count}",
			    //currentPageDropDownTemplate: "ページ ${dropdown} / ${count}",
			    currentPageDropDownTooltip: "ページ インデックスの選択",
			    pageSizeDropDownTooltip: "ページのレコード数の選択",
			    pagerRecordsLabelTooltip: "現在のレコード範囲",
			    prevPageTooltip: "前のページ",
			    nextPageTooltip: "次のページ",
			    firstPageTooltip: "最初のページ",
			    lastPageTooltip: "最後のページ",
			    pageTooltipFormat: "ページ ${index}",
			    pagerRecordsLabelTemplate: "${startRecord} - ${endRecord} / ${recordCount} レコード",
			    invalidPageIndex: "指定したページ インデックスが無効です。0 以上およびページ総数未満のページ インデックスを使用してください。"
		    }
	    });

    $.ig.GridSelection = $.ig.GridSelection || {};

    $.extend($.ig.GridSelection, {
        locale: {
            persistenceImpossible: "選択の永続化に異なる構成を使用してください。グリッドの primaryKey オプションを構成してください。"
        }
    });

	    $.ig.GridRowSelectors = $.ig.GridRowSelectors || {};

	    $.extend($.ig.GridRowSelectors, {

		    locale: {
		        selectionNotLoaded: "igGridSelection は初期化されていません。選択をグリッドで有効にしてください。",
		        columnVirtualizationEnabled: "RowSelectors に異なる仮想化設定を使用してください。rowVirtualization を使用し、virtualizationMode を continuous に設定してください。",
		        selectedRecordsText: "${checked} レコードを選択しました。",
		        deselectedRecordsText: "${unchecked} レコードを選択解除しました。",
		        selectAllText: "${totalRecordsCount} レコードをすべて選択",
		        deselectAllText: "${totalRecordsCount} レコードをすべて選択解除",
		        requireSelectionWithCheckboxes: "チェックボックスが有効な場合、選択動作が必要です。"
		    }
	    });

	    $.ig.GridSorting = $.ig.GridSorting || {};

	    $.extend($.ig.GridSorting, {

		    locale: {
			    sortedColumnTooltipFormat: '${direction}で並べ替え済み',
			    unsortedColumnTooltip: '列の並べ替え',
			    ascending: '昇順',
			    descending: '降順',
			    modalDialogSortByButtonText: '並べ替える',
			    modalDialogResetButton: "リセット",
			    modalDialogCaptionButtonDesc: "クリックして降順に並べ替える",
			    modalDialogCaptionButtonAsc: "クリックして昇順に並べ替える",
			    modalDialogCaptionButtonUnsort: "クリックして並べ替えを解除する",
			    featureChooserText: "並べ替え",
			    modalDialogCaptionText: "複数列の並べ替え",
			    modalDialogButtonApplyText: '適用',
			    modalDialogButtonCancelText: 'キャンセル',
			    sortingHiddenColumnNotSupport: '指定した列が非表示のため並べ替えできません。並べ替えの前に showColumn() メソッドを使用してください。',
			    featureChooserSortAsc: '昇順',
			    featureChooserSortDesc: '降順'
			    //modalDialogButtonSlideCaption: "クリックして並べ替えた列の表示状態を切り替える"
		    }
	    });

	    $.ig.GridSummaries = $.ig.GridSummaries || {};

	    $.extend($.ig.GridSummaries, {
		    locale: {
			    featureChooserText: "集計非表示",
			    featureChooserTextHide: "集計の表示",
			    dialogButtonOKText: 'OK',
			    dialogButtonCancelText: 'キャンセル',
			    emptyCellText: '',
			    summariesHeaderButtonTooltip: '集計の表示/非表示',
			    defaultSummaryRowDisplayLabelCount: 'カウント',
			    defaultSummaryRowDisplayLabelMin: '最小値',
			    defaultSummaryRowDisplayLabelMax: '最大値',
			    defaultSummaryRowDisplayLabelSum: '合計',
			    defaultSummaryRowDisplayLabelAvg: '平均',
			    defaultSummaryRowDisplayLabelCustom: 'カスタム',
			    calculateSummaryColumnKeyNotSpecified: "列キーがありません。集計の計算に列キーを指定してください。",
			    featureChooserNotReferenced: "FeatureChooser への参照がありません。プロジェクトに infragistics.ui.grid.featurechooser.js を含めるか、結合スクリプト ファイルのいずれかを使用してください。"
		    }
	    });

	    $.ig.GridUpdating = $.ig.GridUpdating || {};

	    $.extend($.ig.GridUpdating, {
		    locale: {
			    doneLabel: 'OK',
			    doneTooltip: '編集を終了して更新します',
			    cancelLabel: 'キャンセル',
			    cancelTooltip: '更新せずに編集を終了します',
			    addRowLabel: '新規行の追加',
			    addRowTooltip: '新しい行を追加',
			    deleteRowLabel: '行の削除',
			    deleteRowTooltip: '行の削除',
			    igTextEditorException: 'グリッドの文字列型の列を更新できません。最初に ui.igTextEditor を読み込んでください。',
			    igNumericEditorException: 'グリッドの数値型の列を更新できません。最初に ui.igNumericEditor を読み込んでください。',
			    igCheckboxEditorException: 'グリッドのチェックボックス型の列を更新できません。最初に ui.igCheckboxEditor を読み込んでください。',
			    igCurrencyEditorException: 'グリッドの通貨書式の数値型の列を更新できません。最初に ui.igCurrencyEditor を読み込んでください。',
			    igPercentEditorException: 'グリッドのパーセンテージ書式の数値型の列を更新できません。最初に ui.igPercentEditor を読み込んでください。',
			    igDateEditorException: 'グリッドの日付型の列を更新できません。最初に ui.igDateEditor を読み込んでください。',
			    igDatePickerException: 'グリッドの日付型の列を更新できません。最初に ui.igDatePicker を読み込んでください。',
			    igComboException: 'グリッドにコンボを使用できません。最初に ui.igCombo を読み込んでください。',
			    igRatingException: 'グリッドにエディターとして igRating を使用できません。最初に ui.igRating を読み込んでください。',
			    igValidatorException: 'igGridUpdating で定義されるオプションと検証をサポートできません。最初に ui.igValidator を読み込んでください。',
			    editorTypeCannotBeDetermined: '更新操作には異なる構成が必要です。グリッドの primaryKey オプションを構成してください。',
			    noPrimaryKeyException: '更新操作には異なる構成が必要です。グリッドの primaryKey オプションを構成してください。',
			    hiddenColumnValidationException: '非表示の列および有効にした検証の行を編集できません。行編集の前に showColumn() メソッドを使用するか列の検証を無効にしてください。',
			    dataDirtyException: 'グリッドに保留中のトランザクションがあるため、データの描画に影響する可能性があります。igGrid の autoCommit オプションを有効にするか、dataDirty イベントで false を返す処理をしてください。このイベントの処理中にオプションで commit() メソッドを呼び出すこともできます。',
			    recordOrPropertyNotFoundException: '指定したレコードまたはプロパティが見つかりませんでした。検索の条件を確認して必要に応じて調整してください。',
			rowEditDialogCaptionLabel: '行データの編集',
			excelNavigationNotSupportedWithCurrentEditMode: "ExcelNavigation に異なる構成を使用してください。editMode は cell または row に設定してください。",
			columnNotFound: "指定した列キーが表示列コレクションで見つからないか、指定したインデックスが範囲外です。",
			rowOrColumnSpecifiedOutOfView: "行または列が表示範囲外のため、指定した行または列の編集を開始できません。行または列がその他のページにあるか、その他の仮想化フレームにあります。",
			editingInProgress: "行またはセルは現在編集中です。他の更新プロシージャは、現在の編集が終了するまで開始できません。",
			undefinedCellValue: 'undefined はセルの値に設定できません。',
			addChildTooltip: '子行の追加',
			multiRowGridNotSupportedWithCurrentEditMode: "グリッドで複数行レイアウトが有効の場合、ダイアログ編集モードのみサポートされます。"
		    }
	    });

        $.ig.ColumnMoving = $.ig.ColumnMoving || {};

        $.extend($.ig.ColumnMoving, {
            locale: {
                movingDialogButtonApplyText: '適用',
                movingDialogButtonCancelText: 'キャンセル',
                movingDialogCaptionButtonDesc: '下へ移動',
                movingDialogCaptionButtonAsc: '上へ移動',
                movingDialogCaptionText: '列の移動',
                movingDialogDisplayText: '列の移動',
                movingDialogDropTooltipText: "ここへ移動",
                movingDialogCloseButtonTitle: '「列の移動」ダイアログを閉じる',
                dropDownMoveLeftText: '左へ移動',
                dropDownMoveRightText: '右へ移動',
                dropDownMoveFirstText: '最初へ移動',
                dropDownMoveLastText: '最後へ移動',
                featureChooserNotReferenced: "FeatureChooser への参照がありません。プロジェクトに infragistics.ui.grid.featurechooser.js を含めるか、結合スクリプト ファイルのいずれかを使用してください。",
                movingToolTipMove: '移動',
                featureChooserSubmenuText: '移動'
            }
        });

        $.ig.ColumnFixing = $.ig.ColumnFixing || {};

        $.extend($.ig.ColumnFixing, {
            locale: {
                headerFixButtonText: 'この列を固定',
                headerUnfixButtonText: 'この列の固定解除',
                featureChooserTextFixedColumn: '列の固定',
                featureChooserTextUnfixedColumn: '列の固定解除',
                groupByNotSupported: 'ColumnFixing に異なる構成を使用してください。GroupBy 機能を無効にしてください。',
                virtualizationNotSupported: 'ColumnFixing の仮想化設定に rowVirtualization を使用してください。',
                columnVirtualizationNotSupported: 'ColumnFixing の仮想化設定で columnVirtualization を無効にしてください。',
                columnMovingNotSupported: 'ColumnFixing に異なる構成を使用してください。ColumnMoving を無効にしてください。',
                hidingNotSupported: 'ColumnFixing に異なる構成を使用してください。Hiding 機能を無効にしてください。',
                hierarchicalGridNotSupported: 'igHierarchicalGrid で ColumnFixing はサポートされません。ColumnFixing を無効にしてください。',
                responsiveNotSupported: 'ColumnFixing に異なる構成を使用してください。Responsive 機能を無効にしてください。',
                noGridWidthNotSupported: '列固定に他の構成を使用してください。グリッド幅にパーセンテージまたはピクセル単位の数値を設定してください。',
                gridHeightInPercentageNotSupported: '列固定に他の構成を使用してください。グリッドの高さはピクセルで設定してください。',
                defaultColumnWidthInPercentageNotSupported: "ColumnFixing に異なる構成を使用してください。デフォルトの列幅はピクセル単位の数値で設定してください。",
                columnsWidthShouldBeSetInPixels: 'ColumnFixing の列幅を変更してください。キー {key} のある列幅はピクセルで設定してください。',
                unboundColumnsNotSupported: 'ColumnFixing に異なる構成を使用してください。UnboundColumns を無効にしてください。',
                excelNavigationNotSupportedWithCurrentEditMode: "ExcelNavigation に異なる構成を使用してください。editMode は cell または row に設定してください。",
                initialFixingNotApplied: '初期化の固定設定を {0} キーの列に適用できませんでした。理由: {1}', // {0} is placeholder for columnKey, {1} error message
                setOptionGridWidthException: 'グリッドの幅の値が無効です。固定列がある場合、固定されていない列の表示可能な領域の幅を minimalVisibleAreaWidth の値以上に設定する必要があります。',
                internalErrors: {
                    none: 'グリッドが正しく構成されました。',
                    notValidIdentifier: '指定した列キーは有効ではありません。定義済みグリッド列のいずれかのキーと一致する列キーを使用してください。',
                    fixingRefused: 'この列の固定は現在サポートされていません。他の表示列の固定解除または非表示の固定解除列で最初に showColumn() メソッドを使用してください。 ',
                    fixingRefusedMinVisibleAreaWidth: 'この列は固定できません。グリッドの幅が列を固定するための利用可能なスペースを超えています。',
                    alreadyHidden: 'この列の固定または固定解除は現在できません。showColumn() メソッドは、最初に列に使用してください。',
                    alreadyUnfixed: 'この列はすでに固定解除されています。',
                    alreadyFixed: 'この列はすでに固定されています。',
                    unfixingRefused: '現在この列の固定解除はできません。showColumn() メソッドは、最初に非表示の固定列に使用してください。',
                    targetNotFound: 'キー {key} のあるターゲット列がありません。検索でキーが使用されることを確認し、必要に応じて変更してください。'
                }
            }
        });

    $.ig.GridAppendRowsOnDemand = $.ig.GridAppendRowsOnDemand || {};

    $.extend($.ig.GridAppendRowsOnDemand, {
    	    locale: {
    	        loadMoreDataButtonText: 'その他のデータを読み込む',
    	        appendRowsOnDemandRequiresHeight: 'AppendRowsOnDemand に他の構成を使用してください。グリッドの高さを設定してください。',
    	        groupByNotSupported: 'AppendRowsOnDemand に他の構成を使用してください。GroupBy を無効にしてください。',
    	        pagingNotSupported: 'AppendRowsOnDemand に他の構成を使用してください。ページングを無効にしてください。',
    	        cellMergingNotSupported: 'AppendRowsOnDemand に他の構成を使用してください。CellMerging を無効にしてください。',
    	        virtualizationNotSupported: 'AppendRowsOnDemand に他の構成を使用してください。仮想化を無効にしてください。'
    	    }
        });


    $.ig.igGridResponsive = $.ig.igGridResponsive || {};

    $.extend($.ig.igGridResponsive, {
    	locale: {
    	    fixedVirualizationNotSupported: 'レスポンシブ機能の仮想化設定で virtualizationMode を continuous に設定してください。'
    	}
    });

    $.ig.igGridMultiColumnHeaders = $.ig.igGridMultiColumnHeaders || {};

    $.extend($.ig.igGridMultiColumnHeaders, {
    	locale: {
    	    multiColumnHeadersNotSupportedWithColumnVirtualization: '複数列ヘッダーの構成で columnVirtualization を無効にしてください。'
    	    }
        });

    }
})(jQuery);


/*!@license
* Infragistics.Web.ClientUI Grid 16.1.20161.2145
*
* Copyright (c) 2011-2016 Infragistics Inc.
*
* http://www.infragistics.com/
*
* Depends on:
*	jquery-1.9.1.js
*	jquery.ui.core.js
*	jquery.ui.widget.js
*	infragistics.dataSource.js
*	infragistics.ui.shared.js
*	infragistics.templating.js
*	infragistics.util.js
*/
/*global jQuery, MSApp */
/*jshint -W106 */
if (typeof jQuery !== "function") {
	throw new Error("jQuery is undefined");
}
(function ($) {
	var _hovTR, _aNull = function (val) { return val === null || val === undefined; };
	/*
		igGrid is a widget based on jQuery UI that provides Excel like functionality by rendering data in tabular form
		and includes support for paging, sorting, filtering and selection (both local and remote)
		the widget can be bound to various types of data sources such as JSON, XML, Remote WCF/REST services, etc.
	*/
	$.widget("ui.igGrid", {
		css: {
			/* classes applied to the top container element */
			"baseClass": "ui-widget ui-helper-clearfix ui-corner-all",
			/* Widget content class applied to various content containers in the grid */
			"baseContentClass": "ui-widget-content",
			/* class applied to the top container element */
			"gridClasses": "ui-iggrid",
			/* classes applied to the TBODY, and inherited through css for the records */
			"recordClass": "ui-ig-record ui-iggrid-record",
			/* classes applied on alternate records */
			"recordAltClass": "ui-ig-altrecord ui-iggrid-altrecord",
			/* classes applied to the grid header elements */
			"headerClass": "ui-iggrid-header ui-widget-header",
			/* classes applied to the header text container */
			"headerTextClass": "ui-iggrid-headertext",
			/* classes which will be applied to the grid header cell element when feature icon is rendered */
			"headerCellFeatureEnabledClass": "ui-iggrid-headercell-featureenabled",
			/* jQuery UI class applied to the grid header elements */
			"baseHeaderClass": "ui-widget-header",
			/* classes applied to the TABLE which holds the grid records */
			"gridTableClass": "ui-iggrid-table ui-widget-content",
			"mrlGridTableClass": "ui-iggrid-table-mrl ui-widget-content",
			/* when fixed headers is enabled, this class is applied to the table that holds the header TH elements */
			"gridHeaderTableClass": "ui-iggrid-headertable",
			"mrlGridHeaderTableClass": "ui-iggrid-headertable-mrl",
			/* when fixed footers is enabled, this class is applied to the table that holds the footer TD elements */
			"gridFooterTableClass": "ui-iggrid-footertable ui-widget-footer",
			/* when fixed headers is enabled, this class is applied to the table that holds the footer elements */
			"gridFooterClass": "",
			/* when no headers are shown or fixed headers is false, the caption (if any) needs to be rendered in a separate table */
			"gridCaptionTableClass": "ui-iggrid-captiontable",
			/* classes applied to the element that's on top of the header that has some description */
			"gridHeaderCaptionClass": "ui-iggrid-headercaption ui-widget-header ui-corner-top",
			/* class applied to the TABLE's TBODY holding data records */
			"gridTableBodyClass": "ui-iggrid-tablebody",
			/* classes applied to the scrolling div container when width and height are defined and scrollbars is 'true' */
			"gridScrollDivClass": "ui-iggrid-scrolldiv ui-widget-content",
			/* classes applied to the scrolling div container when virtualization is enabled */
			"gridVirtualScrollDivClass": "ui-iggrid-virtualscrolldiv",
			/* classes applied to the grid footer caption contents */
			"gridFooterCaptionClass": "ui-iggrid-footercaption",
			/* classes applied to the deleted rows of grid before commit */
			deletedRecord: "ui-iggrid-deletedrecord",
			/* classes applied to the modified rows of grid before commit */
			modifiedRecord: "ui-iggrid-modifiedrecord",
			/* class appplied to the grid element when RTL is enabled */
			rtl: "ui-iggrid-rtl",
			/* class appplied to the hidden DIV container used for measurement of width of auto resizable columns*/
			"gridMeasurementContainerClass": "ui-iggrid-measurement-container"
		},
		options: {
			/* type="string|number|null"
				string The widget width can be set in pixels (px) and percentage (%).
				number The widget width can be set as a number
				null type="object" will stretch to fit data, if no other widths are defined
			*/
			width: null,
			/* type="string|number|null" This is the total height of the grid, including all UI elements - scroll container with data rows, header, footer, filter row -  (if any), etc.
				string The widget height can be set in pixels (px) and percentage (%).
				number The widget height can be set as a number
				null type="object" will stretch vertically to fit data, if no other heights are defined
			*/
			height: null,
			/* type="bool" If autoAdjustHeight is set to false, the options.height will be set only on the scrolling container, and all other UI elements such as paging footer / filter row/ headers will add on top of that, so the total height of the grid will be more than this value - the height of the scroll container (content area) will not be dynamically calculated. Setting this option to false will usually result in a lot better initial rendering performance for large data sets ( > 1000 rows rendered at once, no virtualization enabled), since no reflows will be made by browsers when accessing DOM properties such as offsetHeight. */
			autoAdjustHeight: true,
			/* type="string|number" used for virtualization, this is the average value in pixels (default) that will be used to calculate how many rows and which ones to render as the end user scrolls. Also all rows' height will be automatically equal to this value
				string The avarage row height can be set in pixels (25px).
				number The avarage row height can be set as a number (25).
			*/
			avgRowHeight: 25,
			/* type="string|number" used for virtualization, this is the average value in pixels for a column width
				string The avarage column width can be set in pixels (25px).
				number The avarage column width can be set as a number (25).
			*/
			avgColumnWidth: null,
			/* type="string|number" Default column width that will be set for all columns.
				string The default column width can be set in pixels (px).
				number The default column width can be set as a number.
			*/
			defaultColumnWidth: null,
			/* type="bool" if no columns collection is defined, and autoGenerateColumns is set to true, columns will be inferred from the data source. If autoGenerateColumns is not explicitly set and columns has at least one column defined then autoGenerateColumns is automatically set to false */
			autoGenerateColumns: true,
			/* type="bool" Enables/disables virtualization. Virtualization can greatly enhance rendering performance. If enabled, the number of actual rendered rows (DOM elements) will be constant and related to the visible viewport of the grid. As the end user scrolls, those DOM elements will be dynamically reused to render the new data. */
			virtualization: false,
			/* type="fixed|continuous" Determines virtualization mode
				fixed type="string" renders only the visible rows and/or columns in the grid. On scrolling the same rows and/or columns are updated with new data from the data source.
				continuous type="string" renders a pre-defined number of rows in the grid. On scrolling the continuous virtualization loads another portion of rows and disposes the current one.
			*/
			virtualizationMode: "fixed",
			/* type="bool" this is an internal option and should not be used. */
			requiresDataBinding: true,
			/* type="bool" option to enable virtualization for rows only (vertical) */
			rowVirtualization: false,
			/* type="bool" option to enable virtualization for columns only (horizontal) */
			columnVirtualization: false,
			/* type="number" number of pixels to move the grid when virtualization is enabled, and mouse wheel scrolling is performed over the virtual grid area. The "null" value will assume this is set to avgRowHeight */
			virtualizationMouseWheelStep: null,
			/* type="bool" If this option is set to true, the height of the grid row will be calculated automatically based on the average row height and the visible virtual records. If no average row height is specified, one will be calculated automatically at runtime.
			*/
			adjustVirtualHeights: false,
			/* type="string" *** IMPORTANT DEPRECATED ***
			This option has been deprecated as of the 14.1 release. The igGrid now uses column templates for individual column templating.
			jQuery templating style template that will be used to render data records */
			rowTemplate: null,
			/* type="bool" *** IMPORTANT DEPRECATED ***
			This option has been deprecated as of the 12.1 release. The igGrid now uses the custom Infragistics templating engine by default.
			custom high-performance rendering will be used for rendering by default. jQuery Templating plugin can be used and enabled by setting this option to true. This will allow usage of column / row templates in jQuery Templating style. If virtualization is enabled, it is advised to keep this option to "false", in order to have better scrolling/rendering performance
			*/
			jQueryTemplating: false,
			/* type="infragistics|jsRender" the templating engine that will be used to render the grid
				infragistics type="string" the grid will use the Infragistics Templating engine to render its content and specific parts of the UI
				jsRender type="string" the grid will use jsRender to render its content and specific parts of the UI
			*/
			templatingEngine: "infragistics",
			/* type="array" an array of column objects */
			columns: [
				{
					/* type="string" Column header text */
					headerText: null,
					/* type="string" column key (property in the data source to which the column is bound) */
					key: null,
					/* type="string|function" Reference to a function (string or function) which will be used for formatting the cell values. The function should accept a value and return the new formatted value.
					string type="string" string which will be used for formatting
					function type="function" function which will be used for formatting the cell values. The function should accept a value
					*/
					formatter: null,
					/* type="string" Sets gets format for cells in column. Default value is null.
						If dataType is "date", then supported formats are following: "date", "dateLong", "dateTime", "time", "timeLong", "MM/dd/yyyy", "MMM-d, yy, h:mm:ss tt", "dddd d MMM", etc.
						If dataType is "number", then supported numeric formats are following: "number", "currency", "percent", "int", "double", "0.00", "#.0####", "0", "#.#######", etc.
						The value of "double" will be similar to "number", but with unlimited maximum number of decimal places.
						The format patterns and rules for numbers and dates are defined in $.ig.regional.defaults object.
						If dataType is "string" or not set, then format is rendered as it is with replacement of possible "{0}" flag by value in cell. Example, if format is set to "Name: {0}" and value in cell is "Bob", then value will appear as "Name: Bob"
						If value is set to "checkbox", then checkboxes are used regardless of renderCheckboxes option of igGrid. That has effect only when dataType option of column is set to "bool".
					*/
					format: null,
					/* type="string|number|bool|date|object" data type of the column cell values
						string
						number
						bool
						date
						object
					*/
					dataType: "string",
					/* type="string|number" Width of the column in pixels or percentage. Can have optional 'px' at the end. Can also be set as '*', in which case the width autofits based on the content of the column cells (including the header text).
						If width is not defined and defaultColumnWidth is set, it is assumed for all columns.
						string The column width can be set in pixels (px), percentage (%) or as '*' in order to auto-size based on the cells and header content.
						number The column width can be set as a number
					*/
					width: null,
					/* type="bool" Initial visibility of the column. A column can be hidden without the Hiding feature being enabled but there will be no UI for unhiding it. Columns can be defined as hidden in the options of the Hiding feature as well and those definitions take precedence.*/
					hidden: false,
					/* type="string" Sets a template for an individual column. the contents of the template should be the HTML markup that goes inside the table cell, without any <td> and </td> tags included in front and at the end. The syntax of the template, when referencing data keys and using conditional expressions is the same as the one for rowTemplate */
					template: null,
					/* type="bool" Sets whether column is bound to the datasource*/
					unbound: false,
					/* type="array" array of other column definitions. If the column has the property group than the grid has multi column headers*/
					group: [],
					/* type="number" *** IMPORTANT DEPRECATED ***
					This option has been deprecated as of the June 2016 service release.
					Adjust span of multi column header cell. Use option rowSpan. */
					rowspan: 0,
					/* type="string|function" a reference or name of a javascript function which will calculate the value based on other cell values in the same row when column is unbound
					string type="string" name of a javascript function
					function type="function" reference to javascript function
					*/
					formula: null,
					/* type="array" array of values which could be set for unbound columns at init time */
					unboundValues: null,
					/* type="auto|manual" update mode of the unbound column(this option is applied ONLY when option formula is set). Auto update unbound column value whenever the record/cell is updated*/
					unboundValuesUpdateMode: "auto",
					/* type="string" space-separated list of CSS classes to be applied on the header cell of this column */
					headerCssClass: null,
					/* type="string" space-separated list of CSS classes to be applied on the data cells of this column. */
					columnCssClass: null,
					/* type="string|function" This option is applicable only for columns with dataType="object". Reference to a function (string or function) that can be used for complex data extraction from the data records, whose return value will be used for all data operations associated with this column.*/
					mapper: null,
					/* type="number" Specifies the row index of the cell in a multi-row-layout configuration. All columns must have this property set for the multi-row-layout mode to be enabled. */
					rowIndex: null,
					/* type="number" Specifies the column index of the cell in a multi-row-layout configuration. All columns must have this property set for the multi-row-layout mode to be enabled. */
					columnIndex: null,
					/* type="number" Specifies the colSpan of the cell in a multi-row-layout configuration. colSpan 0 is not supported and will be changed to 1 by the grid. */
					colSpan: 1,
					/* type="number" Specifies the rowSpan of the cell in a multi-row-layout configuration. rowSpan 0 is not supported and will be changed to 1 by the grid. If multi-row-layout is not used but multi-column-header is set then this option is used to adjust span of header cell*/
					rowSpan: 1
				}
			],
			/* type="object" can be any valid data source accepted by $.ig.DataSource, or an instance of an $.ig.DataSource itself */
			dataSource: null,
			/* type="string" specifies a remote URL as a data source, from which data will be retrieved using an AJAX call ($.ajax)*/
			dataSourceUrl: null,
			/* type="string" explicitly set data source type (such as "json"). Please refer to the documentation of $.ig.DataSource and its type property */
			dataSourceType: null,
			/* type="string" see $.ig.DataSource. This is basically the property in the responses where data records are held, if the response is wrapped */
			responseDataKey: null,
			/* type="string" See $.ig.DataSource. Property in the response specifying the total number of records on the server. */
			responseTotalRecCountKey: null,
			/* type="string" specifies the HTTP verb to be used to issue the request */
			requestType: "GET",
			/* type="string" content type of the response. See http://api.jquery.com/jQuery.ajax/ => contentType */
			responseContentType: "application/json; charset=utf-8",
			/* type="bool" option controlling the visibility of the grid header */
			showHeader: true,
			/* type="bool" option controlling the visibility of the grid footer */
			showFooter: true,
			/* type="bool" headers will be fixed if this option is set to true, and only the grid data will be scrollable. If virtualization is enabled, fixedHeaders will always act as if it's true, no matter which value is set */
			fixedHeaders: true,
			/* type="bool" footers will be fixed if this option is set to true, and only the grid data will be scrollable. If virtualization is enabled, fixedFooters will always act as if it's true, no matter which value is set */
			fixedFooters: true,
			/* type="string" caption text that will be shown above the grid header */
			caption: null,
			/* type="array" a list of grid features definitions: sorting, paging, etc. Each feature goes with its separate options that are documented for the feature accordingly */
			features: [
				{
					/* type="string" name of the feature to be added */
					name: null

					/* feature options */
				}
			],
			/* type="number" initial tabIndex attribute that will be set on the container element */
			tabIndex: 0,
			/* type="bool" *** IMPORTANT DEPRECATED ***
			This option has been deprecated as of the June 2015 service release. Accessibility rendering is always performed by the igGrid as of version 14.2. */
			accessibilityRendering: false,
			/* type="bool" if this option is set to false, the data to which the grid is bound will be used "as is" with no additional transformations based on columns defined */
			localSchemaTransform: true,
			/* type="string" primary key name of the column containing unique identifiers */
			primaryKey: null,
			/* type="bool" if true, the transaction log will always be sent in the request for remote data, by the data source. Also this means that if there are values in the log, a POST will be done instead of GET */
			serializeTransactionLog: true,
			/* type="bool" automatically commits the transactions as rows/cells are being edited */
			autoCommit: false,
			/* type="bool" if set to true, the following behavior will take place:
				if a new row is added, and then deleted, there will be no transaction added to the log
				if a new row is added, edited, then deleted, there will be no transaction added to the log
				if several edits are made to a row or an individual cell, this should result in a single transaction
				Note: This option takes effect only when autoCommit is set to false.
			*/
			aggregateTransactions: false,
			/* type="date|number|dateandnumber|true|false" Sets gets ability to automatically format text in cells for numeric and date columns. The format patterns and rules for numbers and dates are defined in $.ig.regional.defaults object.
				date formats only Date columns
				number formats only number columns
				dateandnumber type="string"
				true type="bool" formats Date and number columns
				false type="bool" auto formatting is disabled
			*/
			autoFormat: "date",
			/* type="bool" Gets sets ability to render checkboxes and use checkbox editor when dataType of a column is "bool". That option is not available when jQueryTemplating is used. */
			renderCheckboxes: false,
			/* type="string" URL to which updating requests will be made. If autoCommit is true, updates will be done immediately to the data source, without keeping interim transaction logs */
			updateUrl: null,
			/* Settings related to REST compliant update routine */
			restSettings: {
				/* Settings for create requests */
				create: {
					/* type="string" specifies a remote URL to which create requests will be sent. This will be used for both batch and non-batch, however if template is also set, this URL will only be used for batch requests. */
					url: null,
					/* type="string" specifies a remote URL template. Use ${id} in place of the resource id. */
					template: null,
					/* type="bool" specifies whether create requests will be sent in batches */
					batch: false
				},
				/* Settings for update requests */
				update: {
					/* type="string" specifies a remote URL to which update requests will be sent. This will be used for both batch and non-batch, however if template is also set, this URL will only be used for batch requests. */
					url: null,
					/* type="string" specifies a remote URL template. Use ${id} in place of the resource id. */
					template: null,
					/* type="bool" specifies whether update requests will be sent in batches */
					batch: false
				},
				/* Settings for remove requests */
				remove: {
					/* type="string" specifies a remote URL to which remove requests will be sent. This will be used for both batch and non-batch, however if template is also set, this URL will only be used for batch requests. */
					url: null,
					/* type="string" specifies a remote URL template. Use ${id} in place of the resource id. */
					template: null,
					/* type="bool" specifies whether update requests will be sent in batches */
					batch: false
				},
				/* type="bool" specifies whether the ids of the removed resources are send through the request URI */
				encodeRemoveInRequestUri: true,
				/* type="function" specifies a custom function to serialize content sent to the server. It should accept a single object or an array of objects and return a string. If not specified, JSON.stringify() will be used. */
				contentSerializer: null,
				/* type="string" specifies the content type of the request */
				contentType: "application/json; charset=utf-8"
			},
			/* type="bool" enables/disables rendering of alternating row styles (odd and even rows receive different styling). Note that if a custom jQuery template is set, this has no effect and CSS for the row should be adjusted manually in the template contents.  */
			alternateRowStyles: true,
			/* type="bool"  If autofitLastColumn is true and all columns' widths are specified and their combined width is less than the grid width then the last column width will be automatically adjusted to fill the entire grid. */
			autofitLastColumn: true,
			/* type="bool"  enables/disables rendering of ui-state-hover classes when the mouse is over a record. this can be useful in templating scenarios, for example, where we don't want to apply hover styling to templated content */
			enableHoverStyles: true,
			/* type="bool" enables formatting of the dates as UTC. Note that this may be desirable when the dates are coming from a backend, encoded as UTC. Otherwise, if dates are created on the client (in the browser), most probably keeping enableUTCDates to false is the desired behavior */
			enableUTCDates: false,
			/* type="bool" Merge unbound columns values inside datasource when data source is remote. If true then the unbound columns are merged to the datasource at runtime - indeed DataSource is expanded with the new data and this could cause performance issues when dataSource is huge, if false then the unbound data is sent to the client */
			mergeUnboundColumns: false,
			/* type="bool" When dataSource is string defines whether to set data source of type JSONP */
			jsonpRequest: false,
			/* type="bool" enables/disables check for resizing grid container */
			enableResizeContainerCheck: true,
			/* type="none|desktopOnly|always" Configures how the feature chooser icon should display on header cells - e.g. to display as gear icon or to not show gear icon but on click/tap the header cell to show the feature chooser
			none type="string" Always hide the feature chooser icon; The feature chooser is shown on tapping/clicking the column header
			desktopOnly type="string" Always show the icon on desktop but hide when touch device detected
			always type="string" Always show it in any environment. Chooser is shown when tapping the gear icon or column header
			*/
			featureChooserIconDisplay: "desktopOnly"
		},
		events: {
			/* Event fired when a cell is clicked.
			Function takes arguments evt and ui.
			Use ui.cellElement to get reference to cell DOM element.
			Use ui.rowIndex to get row index.
			Use ui.rowKey to get the row key.
			Use ui.colIndex to get column index of the DOM element.
			Use ui.colKey to get the column key.
			Use ui.owner to get reference to igGrid.
			*/
			cellClick: "cellClick",
			/* Event fired when a cell is right clicked.
			Function takes arguments evt and ui.
			Use ui.cellElement to get reference to cell DOM element.
			Use ui.rowIndex to get row index.
			Use ui.rowKey to get the row key.
			Use ui.colIndex to get column index of the DOM element.
			Use ui.colKey to get the column key.
			Use ui.row to get reference to row DOM element.
			Use ui.owner to get reference to igGrid.
			*/
			cellRightClick: "cellRightClick",
			/* cancel="true" Event fired before data binding takes place.
			Return false in order to cancel data binding.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.dataSource to get reference to the igDataSource object.
			*/
			dataBinding: "dataBinding",
			/* Event fired after data binding is complete.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.dataSource to get reference to the igDataSource object.
			*/
			dataBound: "dataBound",
			/* Event fired before the grid starts rendering (all contents).
			This event is fired only when the grid is being initialized.
			It will not be fired if the grid is rebound to its data
			(for example, when calling the dataBind() API method
			or when changing the page size (when paging is enabled)).

			Return false in order to cancel grid rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			rendering: "rendering",
			/* Event fired after the whole grid widget has been rendered (including headers, footers, etc.).
			This event is fired only when the grid is being initialized.
			It will not be fired if the grid is rebound to its data
			(for example, when calling the dataBind() API method
			or when changing the page size (when paging is enabled)).

			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			rendered: "rendered",
			/* cancel="true" Event fired before the TBODY holding the data records starts its rendering.
			Return false in order to cancel data records rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			dataRendering: "dataRendering",
			/* Event fired after all of the data records in the grid table body have been rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			dataRendered: "dataRendered",
			/* cancel="true" Event fired before the header starts its rendering.
			Return false in order to cancel header rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			headerRendering: "headerRendering",
			/* Event fired after the header has been rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.table to get reference to headers table DOM element.
			*/
			headerRendered: "headerRendered",
			/* cancel="true" Event fired before the footer starts its rendering.
			Return false in order to cancel footer rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			footerRendering: "footerRendering",
			/* Event fired after the footer has been rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.table to get reference to footers table DOM element.
			*/
			footerRendered: "footerRendered",
			/* Event fired after every TH in the grid header has been rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.columnKey to get column key.
			Use ui.th to get reference to header cell DOM element.
			*/
			headerCellRendered: "headerCellRendered",
			/* Event fired before actual data rows (TRs) are rendered.
			Return false in order to cancel rows rendering.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.tbody to get reference to grid's table body.
			*/
			rowsRendering: "rowsRendering",
			/* Event fired after data rows are rendered.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.tbody to get reference to grid's table body.
			*/
			rowsRendered: "rowsRendered",
			/* Event fired after $.ig.DataSource schema has been generated, in case it needs to be modified.
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			Use ui.schema to get reference to data source schema.
			Use ui.dataSource to get reference to data source.
			*/
			schemaGenerated: "schemaGenerated",
			/* Event fired after the columns colection has been modified(e.g. a column is hidden)
			Function takes arguments evt and ui.
			Use ui.owner to get reference to igGrid.
			*/
			columnsCollectionModified: "columnsCollectionModified",
			/*
				event fired if there is an error in the request, when the grid is doing a remote operation,
				such as data binding, paging, sorting, etc.
				use ui.owner to get a reference to the grid
				use ui.message to get the processed error message sent by the server
				use ui.response to get reference to the whole response object
			*/
			requestError: "requestError",
			/*
				fired when the grid is created and the initial structure is rendered (this doesn't necessarily imply the data will be there if the data source is remote)
				use ui.owner to get a reference to the grid
			*/
			created: "igcontrolcreated",
			/*
				fired when the grid is destroyed
				use ui.owner to get a reference to the grid
			*/
			destroyed: "igcontroldestroyed"
		},
		resizeTimeout: 300,
		/* type="number" Max height of scroll container that could be set in IE (used when rowVirtualization is true)*/
		maxScrollContainerHeight: 10737418,
		/* type="boolean" configure whether to clone child element of grid when element is TABLE(necessary in some cases when destroying grid to revert to initial state) */
		cloneChildElements: false,
		widget: function () {
			/* returns the element holding the data records */
			return this.element;
		},
		/* type="boolean" Sets whether the vertical scroll position should be persisted on databind */
		_persistVirtualScrollTop: false,
		_createWidget: function (options) {
			/* !Strip dummy objects from options, because they are defined for documentation purposes only! */
			/* S.S. May 23, 2014 - Bug #172064. As of jQuery UI 1.9.0 the widget options are not copied straight up
			and the options.columns would end up pointing to the prototype.options.columns, therefore adding anything
			in the array will change igGrid.prototype leading to unexpected behavior. The following is safe as we'll create
			the array right after we ensure that it won't just end up being a reference to igGrid.prototype.
			*/
			var cols;
			this.options.columns = null;
			this.options.features = null;
			if (options) {
				if (options.dataSource &&
					($.type(options.dataSource) === "array" || $.type(options.dataSource) === "object")) {
					this.tmpDataSource = options.dataSource;
					options.dataSource = null;
					this._originalOptions = options;
				}
				/* M.H. 28 August 2015 Fix for bug 205195: Horizontal scrollbar is not visible when height is set */
				/* If autoGenerateColumns is not explicitly set and columns has at
				least one column defined then autoGenerateColumns is automatically set to false */
				if (options.autoGenerateColumns === undefined) {
					cols = options.columns;
					if ($.type(cols) === "array" && cols.length) {
						options.autoGenerateColumns = false;
					}
				}
			}
			$.Widget.prototype._createWidget.apply(this, arguments);
		},
		/*_init: function () {
		}, */
		_execSetOptionCallbacks: function (key, value) {
			var callbacks = this._setOptionCallbacks || [], i, len = callbacks.length;
			for (i = 0; i < len; i++) {
				callbacks[ i ].func(key, value);
			}
		},
		_setOption: function (key, value) {
			var header, isScrolling, caption, tmp, cval;
			/* handle new settings and update options hash */
			/* A.T. fix for bug #118369 ensure setting an option with the same value does nothing */
			if (value === this.options[ key ]) {
				return;
			}
			cval = this.options[ key ];
			/* isScrolling = this.options.scrollbars && (this.options.height !== null || this.options.width !== null); */
			/* A.T. 7 Feb. 2011 - usability review changes */
			isScrolling = (this.options.height !== null || this.options.width !== null);
			/* M.H. 18 Aug 2016 Fix for bug 223758: Fixing columns after setting new columns collections is throwing an error */
			if (key === "columns") {
				/* it should be called renderMultiColumnHeader before $.Widget.prototype._setOption.apply(this, arguments);
				in function renderMultiColumnHeader - it is called destroyFeatures - and features are using grid.options.columns */
				this.renderMultiColumnHeader(value);
				/* in renderMultiColumnHeader it is set this.options.columns with the new value*/
				return;
			}
			$.Widget.prototype._setOption.apply(this, arguments);
			/* options that are supported:
			width, height, defaultColumnWidth, dataSource, showHeader, showFooter, header caption
			start by throwing an error for all option changes that aren"t supported after the widget has been created */
			if (key === "virtualization" ||
				key === "autoGenerateColumns" /*|| key === "accessibilityRendering"*/ ||
				key === "rowVirtualization" ||
				key === "columnVirtualization" ||
				key === "fixedHeaders" ||
				key === "scrollbars") {
				throw new Error($.ig.Grid.locale.optionChangeNotSupported.replace("{optionName}", key));
			}
			this._execSetOptionCallbacks(key, value);
			if (key === "width") {
				if (isScrolling === true) {
					if (typeof cval === "string" && cval.indexOf("%") !== -1 && this._gridHasWidthInPixels()) {
						throw new Error($.ig.Grid.locale.widthChangeFromPercentagesToPixelsNotSupported);
					} else if (!(typeof cval === "string" && cval.indexOf("%") !== -1) &&
															this._gridHasWidthInPercent()) {
						throw new Error($.ig.Grid.locale.widthChangeFromPixelsToPercentagesNotSupported);
					}
					this._setGridWidth(value);
					/* M.H. 7 Jun 2016 Fix for bug 220337: Resizing grid in pixels causes white line to appear in the unfixed area when fixing direction is right and virtualization is enabled */
					this._fireInternalEvent("_gridWidthChanged");
				} else {
					throw new Error($.ig.Grid.locale.optionChangeNotScrollingGrid.replace("{optionName}", key));
				}
			} else if (key === "height") {
				// depends if the grid is scrolling or not
				if (isScrolling === true) {
					this.scrollContainer().css("overflow-y", "auto");
					if (this.options.autoAdjustHeight) {
						this.container().css("height", value);
						this._virtualHeightReset = true;
						this._initializeHeights();
					} else {
						if (this.options.virtualization === true ||
							this.options.columnVirtualization === true ||
							this.options.rowVirtualization === true) {
							this._vdisplaycontainer().css("height", value);
							this._scrollContainer().css("height", value);
							this.element.height(value);
						} else {
							this.scrollContainer().css("height", value);
						}
					}
					/* M.H. 22 Apr 2014 Fix for bug #170404: avgRowHeight is not respected when resizing the igGrid */
					if (this.options.virtualization ||
						this.options.rowVirtualization ||
						this.options.columnVirtualization) {
						if (this.options.virtualizationMode === "fixed") {
							//this._avgRowHeight = null;
							this._buildVirtualDom();
							this.virtualScrollTo(this._startRowIndex);
						} else {
							// we should re-render virtual records
							// M.H. 25 Nov 2015 Fix for bug 210177: Resizing the grid at runtime results in less rows that the viewport when row virtualization is enabled
							// this._renderVirtualRecordsContinuous();
							// calling the wrapper function will still recognize the virt mode
							// and call the one commented out, however also notifying features
							// about the rerendering.
							// M.H. 10 Mar 2016 Fix for bug 215624: Vertical scroll position is resetted after you resize the grid height dinamically when using virtualization:continuous.
							tmp = this._persistVirtualScrollTop;
							this._persistVirtualScrollTop = true;
							this._saveFirstVisibleTRIndex();
							this._renderVirtualRecords();
							this._persistVirtualScrollTop = tmp;
							/* M.H. 14 Apr 2015 Fix for bug 192458: When virtualization is enabled and grid height
							is changed on window resize event rows height becomes equal to grid height */
							/* this._updateVirtualScrollContainer(); */
						}
					}
					this._fireInternalEvent("_heightChanged");
					/*
					children = this.container().children();
					height = 0;
					for (i = 0; i < children.length; i++) {
						if (!$(children[i]).attr('id').endsWith('_scroll') && $(children[i]).is(':visible')) {
							height += $(children[i]).outerHeight();
						}
					}
					*/
					/* $('#' + this.element[0].id + '_scroll').height(this.container().height() - height); */
				} else {
					throw new Error($.ig.Grid.locale.optionChangeNotScrollingGrid.replace("{optionName}", key));
				}
			} else if (key === "dataSource") {
				this.options.dataSource = value;
				this.dataBind();
				/* M.H. 18 June 2015 Fix for bug 201338: The columns don't autosize when you change the data source.
				when type of datasource is remote then rendering is async and autoSizeColumns should not be called
				here - e.g. customer should call it in dataRendered event handler */
				if (this._inferOpType() !== "remote") {
					this.autoSizeColumns();
				}
			} else if (key === "showHeader") {
				header = this.headersTable();
				/* M.H. 18 Nov 2014 Fix for bug #185321: Setting showHeader to
				false hides the whole child band instead of hiding the header */
				if (header.length > 0 && header.is("table") && header.find("tbody").length === 0) {
					if (value === true) {
						header.show();
					} else {
						header.hide();
					}
				} else {
					header = this.element.find("thead tr");
					if (value === true) {
						header.show();
					} else {
						header.hide();
					}
				}
			} else if (key === "caption") {
				// M.H. 30 Jan 2014 Fix for bug #116158: Caption property cannot be set dynamically at runtime.
				caption = this._caption();
				if (caption.length === 0) {
					this._renderCaption();
					if (this.options.autoAdjustHeight) {
						this._initializeHeights();
					}
				} else {
					// L.A. 06 July 2012 Fixing bug #116158 Unable to change Caption dynamically at runtime
					caption.text(value);
				}
			} else if (key === "alternateRowStyles") {
				this._renderData();
			}
		},
		_initialized: false,
		_headersInitialized: false,
		_footerInitialized: false,
		_mouseClickEventHandler: function (event, eventToTrigger) {
			var $et = $(event.target), row = $et.closest("tr"),
				key = row.attr("data-id"), describedBy, colKey, $td, res, currGrid,
				/* L.A. 12 July 2012 - Fixing bug #116993 When cell selection is in use and
				the user tries to edit a cell, the 3rd and each subsequent clicks on that cell
				cause the igGrid's cellClick event handler to be fired incorrectly for the 1st
				cell of the row
				L.A. 22 August 2012 - Fixing bug #119477 wrong cell index is returned when a checkbox
				is clicked
				*/
				colIndex = $et.closest("td").index(),
				rowIndex = row.index(),
				/* M.H. 14 May 2014 Fix for bug #171207: igHierarchicalGrid when grouped by column,
				ui.colKey retutns the next columKey insted of the correct one */
				/* dataColIndex = colIndex - row.children("td.ui-iggrid-expandcolumn,th,td[data-skip]").length, */
				grid = this;
			/* M.H. 14 May 2014 Fix for bug #171207: igHierarchicalGrid when grouped by column, ui.colKey retutns the next columKey insted of the correct one
			we should not stop propagation for the click event because of the rowEdit template in updating (which is bound to this click event)
			So in hierarchical grid we check whether the closest(to the clicked cell) grid has id equal to the grid.id()
			this check should be done because when user clicks on the child bands the event is bubbled to the parent child grids */
			if (grid.element.closest(".ui-iggrid-root").data("igGrid")) {
				currGrid = $et.closest(this._isMultiRowGrid() ?
					".ui-iggrid-table-mrl" : ".ui-iggrid-table").data("igGrid");
				if (currGrid && grid.id() !== currGrid.id()) {
					return;
				}
			}
			if (key === "" || key === null || key === undefined) {
				key = rowIndex;
			}
			/* M.H. 6 Jun 2013 Fix for bug #143586: CellClick event doesn"t fire for fixed columns. */
			$td = $(event.target);
			if (!$td.is("td")) {
				$td = $(event.target).closest("td");
			}
			describedBy = $td.attr("aria-describedBy");
			if (describedBy) {
				colKey = describedBy.slice(describedBy.indexOf(this.id() + "_") + this.id().length + 1);
			} else {
				//if td does not have aria-describedBy, then return.
				return;
			}
			if (this._isMultiRowGrid()) {
				colIndex = this.columnByKey(colKey).columnIndex;
			}
			if (grid.hasFixedColumns()) {
				if ($td.length === 1) {
					res = grid.getColumnByTD($td);
					/* {column: visibleColumns[visibleInd], index: visibleInd} */
					if (res !== null) {
						colKey = res.column.key;
						colIndex = res.index;
					}
				}
				}
			/* click for the main grid element; "event" is the browser event */
			/* M.H. 30 Jan 2014 Fix for bug #162408: cellClick event raises on filter textbox */
			if (!$et.closest("tr").parent().is("thead") &&
				($et.is("td") || ($et.closest("td").length === 1 &&
				$et.closest("td").parent().attr("data-container") !== "true"))) {
				/* M.H. 3 Apr 2014 Fix for bug #165339: type of PrimaryKey is always string in cellClick event */
				key = grid._fixPKValue(key);
				if (grid.hasFixedColumns()) {
					row = grid._isFixedElement(row) ?
						row.add(this.rowAt(rowIndex)) : row.add(this.fixedRowAt(rowIndex));
				}
				/* we need to conclude if the element that was clicked is a grid cell or not. As a start this check will be enough. */
				grid._trigger(eventToTrigger, event, {
					"rowIndex": rowIndex,
					"rowKey": key,
					"row": row,
					"colIndex": colIndex,
					"colKey": colKey,
					"cellElement": $td[ 0 ],
					owner: grid
				});
			}
		},
		_create: function () {
			var grid = this, i, container, attr;

			this._mouseClickEventHandlers = {
				"click": function (event) {
					grid._mouseClickEventHandler(event, grid.events.cellClick);
				},
				"contextmenu": function (event) {
					grid._mouseClickEventHandler(event, grid.events.cellRightClick);
				}
			};
			/* M.H. 14 June 2012 Fix for bug #114327 */
			this._isHierarchicalGrid = false;
			this._hasUnboundColumns = false;
			if (this.options._isHierarchicalGrid === true) {
				this._isHierarchicalGrid = true;
			}
			/* M.H. 8 Jan 2014 Fix for bug #159857: Calling igGrid.destroy doesn't remove the
			COLGROUP, TFOOT and THEAD tags as well as some attributes on the placeholder */
			if (this.cloneChildElements && this.element.is("table") && this.element.children().length > 0) {
				// we use clone because we will preserve event binding to the elements(if any) if binging is through javascript
				// if we use innerHtml then it will be faster(better for performance) but will not preserve data binding to the element inside to the table(if any)
				// on the other hand if element is table and it is used huge data content this means that clone and table
				this._initialChildren = this.element.children().clone(true);
			}
			this._initialAttributes = [];
			attr = this.element[ 0 ].attributes;
			for (i = 0; i < attr.length; i++) {
				if (attr[ i ].name !== "id") {
					this._initialAttributes.push({ name: attr[ i ].name, value: attr[ i ].value });
				}
			}
			/* check for RTL */
			/* M.H. 3 Mar 2015 Fix for bug #188215: Memory leak occurs in Sorting feature when grid is
			instantiated on TABLE element trying to get CSS properties via jQuery.css causes memory leaks */
			this._rtl = this.element[ 0 ].style.direction === "rtl";
			this._padding = this._rtl ? "padding-left" : "padding-right";
			if (this.tmpDataSource !== null && this.tmpDataSource !== undefined) {
				this.options.dataSource = this.tmpDataSource;
				this._originalOptions.dataSource = this.tmpDataSource;
			}
			this._testInnerHtml();
			this._setOptionCallbacks = [];
			this._headerInitCallbacks = [];
			this._footerInitCallbacks = [];
			this._cellStyleSubscribers = [];
			this._firstBind = true;
			this._isMultiColumnGrid = false;
			this._unboundValues = {};
			if (!this.options.columns) {
				this.options.columns = [];
			}
			if (!this.options.features) {
				this.options.features = [];
			}
			/* analyze multicolumn headers */
			if (this._isMultiColumnHeader() === true) {
				// get 2 objects - one flat representation of the columns(e.g. used for databinding and by the framework)
				// the other object is tree representation of the columns with some additional properties like colspan, rowspan
				this._isMultiColumnGrid = true;
				this._generateColumnFlatStructure(this.options.columns);
				this._headerCells = [];
			}
			/* I.I. bug fix for 107647 */
			if (this.options.rowVirtualization === true &&
				this.options.virtualizationMode === "continuous") {
				this.options.virtualization = true;
			}
			/*I.I. bug fix for 108205, this is by design, columnVirtualization requires virtualization
			to be switched on. columnVirtualization is not relevant for continuous virtualization */
			if (this.options.columnVirtualization === true) {
				this.options.virtualization = true;
				if (this.options.virtualizationMode === "continuous") {
					throw new Error($.ig.Grid.locale.colVirtualizationDenied);
				}
				this.options.virtualizationMode = "fixed";
			}
			/* virtualization always uses fixed headers */
			/* S.S. May 11, 2012 #103088, Regardless of what the user sets, all virtualization uses
			fixed headers and fixed footers which should properly be recognized by all features */
			if (this.options.virtualization === true ||
				this.options.columnVirtualization === true ||
				this.options.rowVirtualization === true) {
				if (this.options.virtualizationMode === undefined ||
					this.options.virtualizationMode === "") {
					this.options.virtualizationMode = "fixed";
				}
				if (this.options.virtualizationMode === "fixed") {
					this.options.fixedHeaders = true;
					/* M.H. 12 March 2012 Fix for bug #101222 */
					this.options.fixedFooters = true;
				}
			}
			/* check if virtualization is enabled and there are auto-resizable columns */
			/* M.H. 18 June 2015 Fix for bug 201220: The grid does not throw
			exception when column is auto sized and virtualization is enabled. */
			if (this.options.virtualization === true || this.options.rowVirtualization === true) {
				if (this.options.defaultColumnWidth === "*") {
					throw new Error($.ig.Grid.locale.virtualizationNotSupportedWithAutoSizeCols);
				}
				for (i = 0; i < this.options.columns.length; i++) {
					if (this.options.columns[ i ].width === "*") {
						throw new Error($.ig.Grid.locale.virtualizationNotSupportedWithAutoSizeCols);
					}
				}
			}
			/* I.I. bug fix for 104408 */
			if (this.options.virtualization === true && this.options.virtualizationMode === "continuous") {
				this.options.fixedHeaders = true;
			}
			/*
			if (this.options.jQueryTemplating === null && (this.options.virtualization === false && this.options.rowVirtualization === false)) {
				this.options.jQueryTemplating = true;
			} else if (this.options.jQueryTemplating === null && (this.options.virtualization === true || this.options.rowVirtualization === true)) {
				this.options.jQueryTemplating = false;
			}
			*/
			if ((this.options.height === null ||
				parseInt(this.options.height, 10) <= 0) &&
				this.options.fixedHeaders === true) {
				this.options.fixedHeaders = false;
			}
			/* M.H. 12 March 2012 Fix for bug #101222 */
			if ((this.options.height === null ||
				parseInt(this.options.height, 10) <= 0) &&
				this.options.fixedFooters === true) {
				this.options.fixedFooters = false;
			}
			/*if (this.options.rowLayoutTemplate) { */
			this._analyzeMultiRowLayout();
			/*} */
			/*
			if (this.options.alternateRowStyles === null && (this.options.virtualization === true || this.options.rowVirtualization === true)) {
				this.options.alternateRowStyles = false;
			} else if (this.options.alternateRowStyles === null) {
				this.options.alternateRowStyles = true;
			}
			*/
			/* trigger an internal data bind call */
			this.dataBind(true);

			this.element.bind(this._mouseClickEventHandlers);
			/* auto adjusting heights when resizing and the grid height is defined with a percentage.
			In that case we need to either use a timeout or resize (which only works in IE) */
			/* M.H. 17 Dec 2013 Fix for bug #151256: Performance issues caused by _resizeContainer function */
			if (this.options.enableResizeContainerCheck &&
				((this.options.height !== null &&
				this.options.height.indexOf &&
				this.options.height.indexOf("%") !== -1) ||
				(this.options.width !== null &&
				this.options.width.indexOf &&
				this.options.width.indexOf("%") !== -1) ||
				!this.element.is(":visible"))) {
				// M.H. 26 Jul 2013 Fix for bug #146837: Vertical scroll bar does not display when control inside the Jquery UI tab
				if (!this.element.is(":visible")) {
					this._recheckVisibility = true;
				}
				/* M.H. 17 Dec 2013 Fix for bug #151256: Performance issues caused by _resizeContainer function */
				if ($.ig.util.isIE) {
					container = this.container();
					if (container.length > 0 && container[ 0 ].attachEvent) {
						this._resizeContainerHandler = $.proxy(this.resizeContainer, this);
						container[ 0 ].attachEvent("onresize", this._resizeContainerHandler);
					}
				}
				if (!this._resizeContainerHandler) {
					this._resId = setInterval($.proxy(this.resizeContainer, this), this.resizeTimeout);
				}
			}
			/* set default value for column.hidden */
			for (i = 0; i < this.options.columns.length; i++) {
				if (this.options.columns[ i ].hidden === undefined) {
					this.options.columns[ i ].hidden = false;
				}
			}
			/* listen to soft dispose. If a feature is of local type, but the invoking
			one is of remote type, the local one must be marked as fully dirty
			example: sorting a column when sorting is local and then changing the page index when paging is remote */
			this._uiSoftDirtyHandler = $.proxy(this._onFeaturesSoftDirty, this);
			this.element.bind("iggriduisoftdirty", this._uiSoftDirtyHandler);
			this._oldScrollTop = 0;
			/* trigger created event */
			this.element.trigger(this.events.created, {
				owner: this
			});
			/* set the rtl class */
			if (this._rtl) {
				this.container().addClass(this.css.rtl);
			}
		},
		_fixPKValue: function (val) {
			/* returns parsed to int val when the primary key is of type number(otherwise returns val)
				paramType="string" value of the primary key
				returnType="string|number" value of the primary key
			*/
			var col, data, pk = this.options.primaryKey;
			if (_aNull(val)) {
				return null;
			}
			if (pk) {
				col = this.columnByKey(pk);
				if (col && col.dataType) {
					if (col.dataType === "number" || col.dataType === "numeric") {
						val = parseInt(val, 10);
					}
				} else {
					data = this.dataSource._data;
					if (data && data.length > 0) {
						if (typeof data[ 0 ][ pk ] === "number") {
							val = parseInt(val, 10);
						}
					}
				}
			} else {
				data = this.dataSource._data;
				if (data && data.length > 0) {
					/*jscs:disable*/
					if (typeof data[0].ig_pk === "number") {
						/*jscs:enable*/
						val = parseInt(val, 10);
					}
				}
			}
			return val;
		},
		hasFixedDataSkippedColumns: function () {
			/* returns whether grid has non-data fixed columns(e.g. row selectors column)
				returnType="bool"
			*/
			return !!this._hasFixedDataSkippedColumns;
		},
		hasFixedColumns: function () {
			/* returns true if grid has at least one fixed columns(even if a non-data column - like row-selectors column)
				returnType="bool"
			*/
			return this._hasFixedColumns;
		},
		fixingDirection: function () {
			/* returns the current fixing direction. NOTE - use only if ColumnFixing feature is enabled
				returnType="left|right"
			*/
			return this._fixingDirection;
		},
		isFixedColumn: function (colKey) {
			/* returns whether the column with identifier colKey is fixed
				paramType="number|string" An identifier of the column which should be checked. It can be a key or visible index.
				returnType="bool"
			*/
			var isFixed = false, typeColKey = $.type(colKey), cols;

			if (!this.hasFixedColumns()) {
				return false;
			}
			if (typeColKey === "string") {
				$.each(this._fixedColumns, function (ind, c) {
					if (c.key === colKey) {
						isFixed = true;
						return false;// break
					}
				});
			} else if (typeColKey === "number") {
				cols = this._visibleColumns();
				if (colKey < 0 || colKey >= cols.length) {
					return false;
				}
				isFixed = (cols[ colKey ].fixed === true);
			}
			return isFixed;
		},
		_onFixedColumnsChanged: function (obj) {
			// fire internal event when fixing/unfixing is done
			this._fireInternalEvent("_fixedColumnsChanged", obj);
		},
		_onGroupedColumnsChanging: function (groupedColumns) {
			this._fireInternalEvent("_groupedColumnsChanging", { groupedColumns: groupedColumns });
		},
		_onGroupedColumnsChanged: function (groupedColumns) {
			this._fireInternalEvent("_groupedColumnsChanged", { groupedColumns: groupedColumns });
		},
		_testInnerHtml: function () {
			var t = document.createElement("table");
			try {
				t.innerHTML = "<tr><td> t </td></tr>";
				this._canreplaceinner = true;
			} catch (e) {
				this._canreplaceinner = false;
			}
		},
		resizeContainer: function () {
			/* Called to detect whether grid container is resized. When autoAdjustHeight is true and height of the grid is changed then the height of grid is re-set.
			*/
			var scrollContainerWidth,
				o = this.options, visibilityChanged = false,
				v = o.virtualization === true ||
				o.rowVirtualization === true ||
				o.columnVirtualization === true;
			/* M.H. 26 Jul 2013 Fix for bug #146837: Vertical scroll bar does not display when control
			inside the Jquery UI tab when grid has not been visible on init then we should re-init heights */
			if (this._recheckVisibility && this.element.is(":visible")) {
				this._prevContainerHeight = 0;
				visibilityChanged = true;
				this._recheckVisibility = false;
				/* M.H. 17 Dec 2013 Fix for bug #151256: Performance issues caused by _resizeContainer function */
				if (this._resId &&
					!((this.options.height !== null &&
					this.options.height.indexOf &&
					this.options.height.indexOf("%") !== -1) ||
					(this.options.width !== null &&
					this.options.width.indexOf &&
					this.options.width.indexOf("%") !== -1))) {
					clearInterval(this._resId);
				}
			}
			if (o.autoAdjustHeight && this.container().height() !== this._prevContainerHeight) {
				this._initializeHeights();
				if (v && visibilityChanged) {
					// M.H. 26 Jul 2013 Fix for bug #148282: When continuous virtualization is enabled and grid is initially hidden, on showing scrollbar is not proporly set
					this._avgRowHeight = null;
					/* M.H. 1 June 2015 Fix for bug 194189: Data content doesn’t appear
					if the tabcontaining the igGrid isn’t selected/displayed when data binding.*/
					/* if virtual records are not rendered */
					if (!this.container().find("#" + this.id() +
						" > tbody > tr:not([data-container]):visible:first").length) {
						// in case of fixed virtualization this._virtualDom is empty array - we should delete it so to re-build virtual DOM
						delete this._virtualDom;
						this._renderVirtualRecords();
					}
					this._updateVirtualScrollContainer();
					this._onVirtualVerticalScroll();
				}
			}
			if (o.width !== null && o.height !== null) {
				if (v === false) {
					scrollContainerWidth = this.scrollContainer().width();
				} else {
					scrollContainerWidth = this._vdisplaycontainer().width() + this._scrollbarWidth();
				}
				/* M.H. 8 Aug 2016 Fix for bug 221802: Column headers are resized when a grid inside a tab is displayed if a vertical scrollbar is visible. */
				if (!scrollContainerWidth) {
					return;
				}
				/* A.T. 10 May 2012 - Fix for bug #111407 */
				/* L.A. 31 October 2012 - Fixing bug #125617 */
				/* setting height property on grid causes a horizontal scroll bar placeholder to render */
				if (this._gridInnerWidth !== scrollContainerWidth &&
					(this._gridInnerWidth > 0 || scrollContainerWidth > 0)) {
					this._gridInnerWidth = scrollContainerWidth;
					this._updateHScrollbarVisibility();
				}
				if (v === false && (this.scrollContainer().get(0).scrollHeight >
					this.scrollContainer().get(0).clientHeight) !== this._hasVerticalScrollbar) {
					this._adjustLastColumnWidth();
				}
			}
		},
		_isMultiColumnHeader: function () {
			// checks whether the grid has multicolumn headers - checks whether in columns definition some of the columns on the root level has property group
			var cols = this.options.columns, i;
			for (i = 0; i < cols.length; i++) {
				if (cols[ i ].group !== undefined && cols[ i ].group !== null) {
					return true;
				}
			}
			return false;
		},
		isGroupHeader: function (colKey) {
			/* returns whether the header identified by colKey is multicolumn header(has children)
				paramType="string" value of the column key
				returnType="boolean"
			*/
			if (this._isMultiColumnGrid && !this.columnByKey(colKey)) {
				return true;
			}
			return false;
		},
		_analyzeColumnByKey: function (key) {
			// analyze column and return column object, data index, visualindex, IF MCH is enabled - returns MCH specific properties
			if (_aNull(key)) {
				return null;
			}
			var res,
				isGroupHeader = this.isGroupHeader(key),
				children = isGroupHeader ?
					this._getMultiHeaderColumnById(key).children : [ this.columnByKey(key) ],
				colInfo = this._getColumnInfo(children[ 0 ].key, this.options.columns),
				colMCHInfo = this._isMultiColumnGrid ? this._getColumnInfo(key) : null;

			res = {
				key: key,
				vIndex: this.getVisibleIndexByKey(children[ 0 ].key, true),
				vIndexNonDataCols: this.getVisibleIndexByKey(children[ 0 ].key, false),
				dIndex: colInfo.index,
				children: children,
				colObj: colInfo.column
			};
			/* MCH specific properties */
			if (colMCHInfo) {
				res = $.extend(res,
					{
						dMCHIndex: colMCHInfo.index,
						colMCHObj: colMCHInfo.column,
						isGroupHeader: isGroupHeader
					});
			}
			return res;
		},
		_analyzeColumnMovingObjects: function (colKey, targetKey, after, isToFix) {
			var isMCH = this._isMultiColumnGrid, i, fCols,
				cols = this.options.columns,
				at = {
					dIndex: -1,
					dMCHIndex: -1,
					vIndex: -1
				},
				fdLeft = this.fixingDirection() === "left",
				target = this._analyzeColumnByKey(targetKey),
				resObj = {
					from: this._analyzeColumnByKey(colKey),
					at: at,
					target: target
				};
			if (target) { // if targetKey is set
				at.dIndex = after ?
						target.dIndex + target.children.length :
						target.dIndex;
				at.vIndex = after ?
						target.vIndex + target.children.length :
						target.dIndex;
				if (isMCH) {
					at.dMCHIndex = after ?
						target.dMCHIndex + 1 :
						target.dMCHIndex;
				}
			} else {
				fCols = this._fixedColumns || [];
				if (isToFix) {
					at.dIndex = fdLeft ?
						fCols.length :
						cols.length;
					at.vIndex = this.getVisibleIndexByKey(cols[ at.dIndex ], true);
					if (isMCH) {
						for (i = 0; i < this._oldCols.length; i++) {
							at.dMCHIndex = i;
							if ((!this._oldCols[ i ].fixed && fdLeft) ||
								(this._oldCols[ i ].fixed && !fdLeft)) {
								at.dMCHIndex = i;
								break;
							}
						}
						at.dMCHIndex = fdLeft ? at.dMCHIndex : at.dMCHIndex + 1;
					}
				} else {
					at.dIndex = fdLeft ?
						fCols.length - resObj.from.children.length - 1 :
						0;
					at.vIndex = this.getVisibleIndexByKey(cols[ at.dIndex ], true);
					if (isMCH) {
						for (i = 0; i < this._oldCols.length; i++) {
							if ((!this._oldCols[ i ].fixed && fdLeft) ||
								(this._oldCols[ i ].fixed && !fdLeft)) {
								at.dMCHIndex = i - 1;
								break;
							}
						}
					}
				}
			}
			return resObj;
		},
		/*
		cols - current level of cols (the function is recursive),
		newCols - flat list of columns at level 0
		level - current level, oldCols - cols(which are passed to the grid.options.columns) with analyzed, colspan and level
		children - list of children for each multicolumn header
		*/
		_analyzeMultiColumnHeaders: function (cols, newCols, level, oldCols, children, isHidden) {
			// analyze multicolumn headers - get colspans for multicolumn headers, set level for each header column and gets flat list of columns on level 0
			// sets identifier for each column - if column does not have key(even if multicolumn header) then it is set auto generated key
			// we need of keys to get easier cells and in some features - like resizing, hiding, column moving
			var i, j, res, colsLength = cols.length, colspan = 0,
				ind = [], groupChildren = [], identifier, hidden;
			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].group !== undefined && cols[ i ].group !== null) {
					if (cols[ i ].key !== undefined && cols[ i ].key !== null) {
						// M.H. 4 July 2012 Fix for bug #116254 - column indentifier should be string
						identifier = cols[ i ].key.toString();
					} else {
						// M.H. 4 July 2012 Fix for bug #116254 - column indentifier should be string
						identifier = (this._multiColumnIdentifier++).toString();
					}
					if (!cols[ i ].identifier) {
						cols[ i ].identifier = identifier;
					}
					groupChildren = [];
					hidden = false;
					if (isHidden === true || cols[ i ].hidden === true) {
						hidden = true;
					}
					res = this._analyzeMultiColumnHeaders(
						cols[ i ].group, newCols, level + 1, oldCols[ i ].group, groupChildren, hidden
					);

					oldCols[ i ].colspan = res.colspan;
					oldCols[ i ].children = groupChildren;
					for (j = 0; j < groupChildren.length; j++) {
						children.push(groupChildren[ j ]);
					}
					ind.push(i);
					colspan += res.colspan;
				} else {
					colspan++;
					oldCols[ i ].level = 0;
					oldCols[ i ].level0 = true;
					if (isHidden === true) {
						cols[ i ].hidden = true;
					}
					children.push(cols[ i ]);
					newCols.push(cols[ i ]);
				}
			}
			for (j = 0; j < ind.length; j++) {
				oldCols[ ind[ j ] ].level = this._maxLevel - level;
			}
			return { colspan: colspan };
		},
		_analyzeMultiRowLayout: function () {
			var i, j, cols = this.options.columns, ri, ci, col, t, res, hasWidths = false;
			/* row, row, row
			cell (cs, rs, key) (col)
			for now we'll expect all or none of the columns to have positions */
			if (!cols.length) {
				return;
			}
			for (i = 0; i < cols.length; i++) {
				ri = cols[ i ].rowIndex;
				ci = cols[ i ].columnIndex;
				if (ci === null || ri === null || isNaN(ri - 0) || isNaN(ci - 0)) {
					return;
				}
			}
			/* we know that all columns have indexes */
			this._rlm = [];
			for (i = 0; i < cols.length; i++) {
				this._addColumnToLayout(cols[ i ]);
				if (cols[ i ].width) {
					hasWidths = true;
				}
			}
			/* t = this._rlm; */
			this._mrl = $.extend(true, [], this._rlm);
			t = this._mrl;
			for (i = 0; i < t.length; i++) {
				for (j = 0; j < t[ i ].length; j++) {
					if (!t[ i ][ j ]) { // checks for null
						throw new Error($.ig.Grid.locale.multiRowLayoutNotComplete);
					}
				}
			}
			if (hasWidths) {
				this._analyzeMCLWidths();
			}
			/* now translate to understandable format */
			this._rlp = [];
			for (i = 0; i < t.length; i++) {
				this._rlp.push([]);
				for (j = 0; j < t[ i ].length; j++) {
					if (t[ i ][ j ] && $.type(t[ i ][ j ]) === "string") {
						col = this.columnByKey(t[ i ][ j ]);
						res = this._getSizeOfMultiSpanCol(i, j, t);
						this._rlp[ i ].push({
							col: col,
							rs: res.rs,
							cs: res.cs
						});
					}
				}
			}
			this._maxCols = this._rlm[ 0 ].length;
		},
		_analyzeMCLWidths: function () {
			var t = this._rlm, i, j, col, colKey, minColSpan, colSpan, isPercentage = false;
			this._colGroupWidths = [];
			isPercentage = this._checkForPercentageWidths();
			for (i = 0; i < t.length; i++) {
				for (j = 0; j < t[ i ].length; j++) {
					colKey = t[ i ][ j ];
					col = this.columnByKey(colKey);
					if (col.width === undefined) {
						col.width = this._resolveWidth(col);
					}
					colSpan = col.colSpan ? col.colSpan : 1;
					/* if we have a width from a colSpan = 1, then we can directly save i in the col group widhts collection */
					if (colSpan === 1) {
						this._colGroupWidths[ col.columnIndex ] = col.width;
					}
				}
			}

			if (isPercentage) {
				$(this.options.columns).each(function () {
					if (this.width && (typeof(this.width) !== "string" || !this.width.contains("%"))) {
						this.width = parseInt(this.width) + "%";
					}
				});
			}
			/*At this point we should have all column widths that can be resolved.
			If we have colGroup widths that aren't resolved yet we will calculate
			them based on the column with the smaller colSpan that has width */
			for (j = 0; j < t[ 0 ].length; j++) {
				if (!this._colGroupWidths[ j ]) {
					for (i = 0; i < t.length; i++) {
						col = this.columnByKey(t[ i ][ j ]);
						if (col.width && (minColSpan === undefined || minColSpan < col.colSpan)) {
							this._colGroupWidths[ j ] = parseInt(col.width) / col.colSpan;
						}
					}
				}
				if (isPercentage && this._colGroupWidths[ j ] &&
					(typeof (this._colGroupWidths[ j ]) !== "string" ||
					!this._colGroupWidths[ j ].contains("%"))) {
					this._colGroupWidths[ j ] = parseInt(this._colGroupWidths[ j ]) + "%";
				}
			}
		},
		_checkForPercentageWidths: function () {
			var isPercentage = false, isPixels = false;
			$(this.options.columns).each(function () {
				if (this.width) {
					if (typeof (this.width) === "string" && this.width.contains("%")) {
						isPercentage = true;
					} else if (this.width && typeof (this.width) === "number" || this.width.contains("px")) {
						isPixels = true;
					}
				}
			});
			if (isPixels && isPercentage) {
				//we have widths in percentage and pixels. This is not supported.
				throw new Error($.ig.Grid.locale.multiRowLayoutMixedWidths);
			}
			return isPercentage;
		},
		_resolveWidthMultiCol: function (col, resolveCol) {
			//This function aims to resolve the distribution of widths in column with colSpan>1.
			//Returns an array of the distributions.
			var subCol, subColsWidths = [], currCol, t = this._rlm, sum,
				cInd, rInd, ind, nullWidths, currSum, nullInd;

			for (cInd = 0; cInd < col.colSpan; cInd++) {
				for (rInd = 0; rInd < t.length; rInd++) {
					if (rInd === col.rowIndex) {
						continue;
					}
					currCol = this.columnByKey(t[ rInd ][ col.columnIndex + cInd ]);
					if (!currCol.width || resolveCol === currCol.key) {
						continue;
					}
					if (this._colGroupWidths[ col.columnIndex + cInd ]) {
						subColsWidths[ cInd ] = this._colGroupWidths[ col.columnIndex + cInd ];
					} else {
						if (currCol.colSpan === 1) {
							subColsWidths[ cInd ] = parseInt(currCol.width);
							this._colGroupWidths[ col.columnIndex + cInd ] = subColsWidths[ cInd ];
							break;
						}
						sum = 0;
						/* calculate subpart */
						for (ind = currCol.columnIndex; ind < currCol.columnIndex + currCol.colSpan; ind++) {
							if (ind === col.columnIndex + cInd) {
								continue;
							}
							subCol = {
								key: currCol.key + ind, rowIndex: currCol.rowIndex, columnIndex: ind, colSpan: 1
							};
							subCol.width = this._resolveWidth(subCol, col.key);
							if (subCol.width) {
								sum += parseInt(subCol.width);
							} else {
								//could not find all subparts of the columns
								sum = 0;
								subColsWidths[ cInd ] = null;
								break;
							}
						}
						if (sum > 0) {
							this._colGroupWidths[ col.columnIndex + cInd ] = parseInt(currCol.width) - sum;
							subColsWidths[ cInd ] = this._colGroupWidths[ col.columnIndex + cInd ];
						} else {
							subColsWidths[ cInd ] = null;
						}
					}
				}
				if (subColsWidths[ cInd ] === undefined) {
					subColsWidths[ cInd ] = null;
				}
			}
			nullWidths = $(subColsWidths).filter(function (index) {
				return subColsWidths[ index ] === null || subColsWidths[ index ] === undefined;
			});
			if (nullWidths.length === 1) {
				//there is exactly 1 width which is not filled for this column and we can calculate it
				currSum = 0;
				$(subColsWidths).each(function (index) {
					if (subColsWidths[ index ] !== undefined && subColsWidths[ index ] !== null) {
						currSum += parseInt(subColsWidths[ index ]);
					} else {
						nullInd = index;
					}
				});
				subColsWidths[ nullInd ] = parseInt(col.width) - currSum;
				this._colGroupWidths[ col.columnIndex + nullInd ] = subColsWidths[ nullInd ];
			}
			return subColsWidths;
		},
		_resolveWidth: function (col, resolveCol) {
			var t = this._rlm,
			colSpan = col.colSpan === undefined || col.colSpan === null ? 1 : col.colSpan,
			columnIndex = col.columnIndex, rowIndex = col.rowIndex,
			rInd = 0, cInd = 0, currWidth, sum, subPartsCount = 0,
			currCol, relColumn, commonPart, width = null;
			if (col.width !== undefined) {
				//if width is set then return it
				return col.width;
			}
			if (col.colSpan === 1 && this._colGroupWidths[ col.columnIndex ]) {
				return this._colGroupWidths[ col.columnIndex ];
			}
			/* otherwise loop thorough the rows */
			for (rInd = 0; rInd < t.length; rInd++) {
				if (rInd === rowIndex) {
					continue;
				}
				/* get the columns that are on the same column index */
				currCol = this.columnByKey(t[ rInd ][ columnIndex ]);
				currCol.colSpan = currCol.colSpan === undefined || currCol.colSpan === null ?
					1 : currCol.colSpan;
				/* if a column on the same column index has no width - continue */
				if (!currCol.width || resolveCol === currCol.key) {
					continue;
				}
				commonPart = this._getCommonColIndexes(col, currCol);
				if (currCol.colSpan === colSpan && commonPart.length === currCol.colSpan) {
					//if we have a column with the same col span, which has width set
					//set the same width for the current column
					width = currCol.width;
					break;
				} else if (commonPart.length < currCol.colSpan && commonPart.length === colSpan) {
					// We have a column with bigger colspan on another row (same colIndex), which has width
					sum = 0;
					for (cInd = currCol.columnIndex; cInd < currCol.columnIndex + currCol.colSpan; cInd++) {
						if (t[ rowIndex ][ cInd ] !== col.key) {
							relColumn = this.columnByKey(t[ rowIndex ][ cInd ]);
							relColumn.colSpan = relColumn.colSpan === undefined || relColumn.colSpan === null ?
								1 : relColumn.colSpan;
							currWidth = relColumn.width;
							/* resolve the width if it's not set */
							if (!currWidth ) {
								if (resolveCol === relColumn.key) {
									//if a column is trying to solve it's width by another column that
									//calls the original column, then return null for the width
									return null;
								}
								currWidth = this._resolveWidth(relColumn, col.key);
								if (!currWidth) {
									//if we cannot resolve the width, return null
									return null;
								}
								relColumn.width = currWidth;
							}
							if (relColumn.colSpan) {
								cInd += relColumn.colSpan - 1;
							}
							commonPart = this._getCommonColIndexes(currCol, relColumn);
							if (commonPart.length !== relColumn.colSpan && commonPart.length > 0) {
								currWidth = this._calcCommonPartSum(relColumn, commonPart, resolveCol);
								if (!currWidth) {
									return null;
								}
								subPartsCount += this._getCommonColIndexes(col, currCol).length;
							} else {
								subPartsCount += this._getCommonColIndexes(col, currCol).length;
							}
							if (!currWidth) {
								break;
							}
							sum += parseInt(currWidth, 10);
						}
					}
					/* if we don't have a missing subpart and we have a valid sum */
					if (sum !== 0 && subPartsCount === currCol.colSpan - commonPart.length) {
						width =  parseInt(currCol.width) - sum;
						break;
					}
				} else if (commonPart.length < colSpan && currCol.colSpan === commonPart.length) {
					//We have a column with smaller colspan that is related to the current column (same colIndex, different rowIndex)
					sum = 0;
					subPartsCount = 0;
					/* loop through the cols reated to the current col and see if we can sum them to get the current col width */
					for (cInd = col.columnIndex; cInd < col.columnIndex + col.colSpan; cInd++) {
						relColumn = this.columnByKey(t[ currCol.rowIndex ][ cInd ]);
						relColumn.colSpan = relColumn.colSpan === undefined || relColumn.colSpan === null ?
							1 : relColumn.colSpan;
						currWidth = relColumn.width;
						/* check if we can resolve the width if it's not set */
						if (!currWidth) {
							currWidth = this._resolveWidth(relColumn, col.key);
							if (!currWidth) {
								continue;
							}
							relColumn.width = currWidth;
						}
						if (relColumn.colSpan) {
							cInd += relColumn.colSpan - 1;
						}
						commonPart = this._getCommonColIndexes(col, relColumn);
						if (commonPart.length !== relColumn.colSpan && commonPart.length > 0) {
							//if we have columns where they only partlially overlap, then we should
							//try to calculate the parts that overlap so that we can apply them as part of the sum.
							if (resolveCol !== currCol.key && resolveCol !== relColumn.key) {
								//calcumate sum
								currWidth = this._calcCommonPartSum(relColumn, commonPart, resolveCol);
								if (!currWidth) {
									//if we cannot determine the sum stop
									break;
									/* return null; */
								}
								subPartsCount += commonPart.length;
							} else {
								//if we're trying to resolve a column's width that depends on
								//the same column that called it then break.try another column.
								break;
							}
						} else {
							subPartsCount += relColumn.colSpan;
						}
						if (!currWidth) {
							break;
						}
						sum += parseInt(currWidth, 10);
					}
					/* if we don't have a missing subpart and we have a valid sum */
					if (sum !== 0 && subPartsCount === colSpan) {
						width = sum;
						break;
					}
				} else if (commonPart.length < colSpan && commonPart.length < currCol.colSpan) {
					//In this case the column whose width we want to resolve and the currColumn only partually overlap
					subPartsCount = 0;
					sum = this._calcCommonPartSum(currCol, commonPart, resolveCol);
					if (!sum) {
						//if we cannot determine the sum stop and return null for this column.
						return null;
					}
					subPartsCount += commonPart.length;
					/* loop through the cols reated to the current col and see if we can sum them to get the current col width */
					for (cInd = col.columnIndex; cInd < col.columnIndex + col.colSpan; cInd++) {
						relColumn = this.columnByKey(t[ currCol.rowIndex ][ cInd ]);
						relColumn.colSpan = relColumn.colSpan === undefined || relColumn.colSpan === null ?
							1 : relColumn.colSpan;
						if (relColumn.key === currCol.key) {
							continue;
						}
						currWidth = relColumn.width;
						if (!currWidth) {
							currWidth = this._resolveWidth(relColumn, col.key);
							if (!currWidth) {
								continue;
							}
							relColumn.width = currWidth;
						}
						commonPart = this._getCommonColIndexes(col, relColumn);
						if (commonPart.length !== relColumn.colSpan && commonPart.length > 0) {
							//if we have columns where they only partlially overlap, then we
							//should try to calculate the parts that overlap so that we can apply them as part of the sum.
							if (resolveCol !== currCol.key && resolveCol !== relColumn.key) {
								currWidth = this._calcCommonPartSum(relColumn, commonPart, resolveCol);
								if (currWidth === null) {
									return null;
								}
								subPartsCount += commonPart.length;
							} else {
								//if we're trying to resolve a column's width that depends on
								//the same column that called it then break. Try another column.
								break;
							}
						} else {
							subPartsCount += relColumn.colSpan;
						}
						if (!currWidth) {
							break;
						}
						sum += parseInt(currWidth, 10);
						if (sum !== 0 && subPartsCount === colSpan) {
							width = sum;
							break;
						}
					}
				}
			}
			return width;
		},
		_getCommonColIndexes: function (col1, col2) {
			//gets indexes where the two passed columns overlap
			var t = this._rlm, indexes = [], i;
			if (col1.colSpan === 1 && t[ col2.rowIndex ][ col1.columnIndex ] === col2.key) {
				indexes[ 0 ] = col1.columnIndex;
				return indexes;
			}
			for (i = 0; i < t[ col1.rowIndex ].length; i++) {
				if (t[ col1.rowIndex ][ i ] === col1.key && t[ col2.rowIndex ][ i ] === col2.key) {
					indexes.push(i);
				}
			}
			return indexes;
		},
		_calcCommonPartSum: function (relColumn, commonPart, resolveCol) {
			var currSum = 0, ind, w, hasNulls = false,
			widthDistribution = this._resolveWidthMultiCol(relColumn, resolveCol);
			if (widthDistribution.length !== relColumn.colSpan) {
				//if we cannot determine the distribution stop and return null for this column
				//we cannot determine its width
				return null;
			}
			for (ind = 0; ind < commonPart.length; ind++) {
				w = widthDistribution[ commonPart[ ind ] - relColumn.columnIndex ];
				if (w) {
					currSum += parseInt(w, 10);
				} else {
					//we have a non-defined value so we cannot calculate the sum of the common parts.
					currSum = 0;
					hasNulls = true;
					break;
				}
			}
			if (hasNulls) {
				//try getting the common part width by substracting the non-common part from the total col sum
				currSum = 0;
				for (ind = 0; ind < widthDistribution.length; ind++) {
					if (!commonPart.contains(ind + relColumn.columnIndex)) {
						currSum += widthDistribution[ ind ];
					}
				}
				if (currSum) {
					currSum = relColumn.width - currSum;
				}
			}
			return currSum;
		},
		_addColumnToLayout: function (col) {
			var t = this._rlm, x, y, colLen, totalWidth, totalHeight, colSpan, rowSpan;
			colLen = t.length ? t[ 0 ].length : 0;
			colSpan = col.colSpan ? col.colSpan : 1; // 0 is considered 1 so the condition is fine
			rowSpan = col.rowSpan ? col.rowSpan : 1; // 0 is considered 1 so the condition is fine
			totalWidth = col.columnIndex + colSpan - 1;
			totalHeight = col.rowIndex + rowSpan - 1;
			/* expand the matrix so it's big enough to add the col */
			if (totalHeight >= t.length) {
				x = t.length - 1;
				while (++x <= totalHeight) {
					t.push(Array.apply(null, new Array(colLen)).map(function () {
						return null;
					}));
				}
			}
			if (totalWidth >= colLen) {
				for (x = 0; x < t.length; x++) {
					for (y = colLen; y <= totalWidth; y++) {
						t[ x ].push(null);
					}
				}
			}
			/* change the matrix values to accomodate the col */
			for (x = col.rowIndex; x <= totalHeight; x++) {
				for (y = col.columnIndex; y <= totalWidth; y++) {
					if (t[ x ][ y ]) {
						throw new Error($.ig.Grid.locale
							.multiRowLayoutColumnError.replace("{key1}", col.key).replace("{key2}", t[ x ][ y ]));
					}
					t[ x ][ y ] = col.key;
				}
			}
			return true;
		},
		_isMultiRowGrid: function () {
			// returns if the grid is rendered in a multi-row-style
			return !!this._rlm;
		},
		/* jshint unused:false */
		_multiRowLayoutRenderingHelper: function (fixed) {
			// simple rendering helper accessor that should return the layout of the specified area
			// until fixing is integrated with mrl, this will simply return this._rlp
			return this._rlp;
		},
		getElementInfo: function (elem) {
		/* returns an object that contains information on the passed Dom element
			paramType="dom" The Dom element or jQuery object which can be a TD or TR element from the grid.
			returnType="object" Object that contains the following information on the passed DOM element:
				rowId - the id of the record associated with the element - if primaryKey is not set this will be null.
				rowIndex - the index (in the DOM) of the row associated with the element.
				recordIndex - index of the data record associated with this element in the current dataView.
				columnObject  - the column object associated with this element ( if the element is tr this will be null)
		*/
			var column = null, rowId = null, rowIndex = null, recordIndex = null;
			if (!(elem instanceof jQuery)) {
				elem = $(elem);
			}
			if (elem.is("td")) {
				column = this.getColumnByTD(elem).column;
				rowIndex = elem.parent("tr").index();
				recordIndex = parseInt(rowIndex / this._recordVerticalSize(), 10);
				if (this.options.primaryKey) {
					rowId = this.dataSource.getCellValue(
						this.options.primaryKey,
						this.dataSource.dataView()[ recordIndex ]
					);
				}
			} else if (elem.is("tr")) {
				rowIndex = elem.index();
				recordIndex = parseInt(rowIndex / this._recordVerticalSize(), 10);
				if (this.options.primaryKey) {
					rowId = this.dataSource.getCellValue(
						this.options.primaryKey,
						this.dataSource.dataView()[ recordIndex ]
					);
				}
			}
			return { column: column, rowId: rowId, rowIndex: rowIndex, recordIndex: recordIndex };
		},
		_recordHorizontalSize: function () {
			return this._rlm && this._rlm.length ?
				this._rlm[ 0 ].length : this._visibleColumns().length;
		},
		_recordVerticalSize: function () {
			return this._rlm ? this._rlm.length : 1;
		},
		_getRowsByRecordIndex: function (recIndex) {
			var rows = [], i, tbody = this.element.find("tbody")[ 0 ];
			for (i = 0 ; i < this._recordVerticalSize() ; i++) {
				rows.push(tbody.rows[ recIndex * this._recordVerticalSize() + i ]);
			}
			return $(rows);
		},
		_getSizeOfMultiSpanCol: function (x, y, rlm) {
			var w = 1, h = 1, i = x + 1, j = y + 1, t = rlm, key = t[ x ][ y ];
			/* search right */
			while (j < t[ x ].length && t[ x ][ j ] === key) {
				t[ x ][ j ] = -1;
				w++;
				j++;
			}
			while (i < t.length && t[ i ][ y ] === key) {
				t[ i ][ y ] = -1;
				h++;
				i++;
			}
			/* change to -1 the submatrix under the i an j axes */
			for (i = x + 1; i < x + h; i++) {
				for (j = y + 1; j < y + w; j++) {
					t[ i ][ j ] = -1;
				}
			}
			return { cs: w, rs: h };
		},
		_getMultiHeaderColumnById: function (id, level, cols) {
			// get multi column header by id
			// if level specified search for the specified level
			var i, colsLength, res = null;
			if (cols === null || cols === undefined) {
				// M.H. 12 Dec 2012 Fix for bug #129344
				if (this._oldCols === null || this._oldCols === undefined) {
					return null;
				}
				cols = this._oldCols;
			}
			colsLength = cols.length;
			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].identifier === id && (level === undefined || cols[ i ].level === level)) {
					return cols[ i ];
				}
				if (cols[ i ].group !== null && cols[ i ].group !== undefined) {
					res = this._getMultiHeaderColumnById(id, level, cols[ i ].group);

					if (res !== null) {
						return res;
					}
				}
			}
			return null;
		},
		_getMaxLevelRecursive: function (level, cols) {
			// get how deep multicolumn headers are
			var i, colsLength = cols.length, ml = level, l, rowspan;
			for (i = 0; i < colsLength; i++) {
				// M.H. 8 Aug 2012 Fix for bug #118599
				rowspan = cols[ i ].rowspan || cols[ i ].rowSpan || 1;
				if (cols[ i ].group !== undefined && cols[ i ].group !== null) {
					l = this._getMaxLevelRecursive(level + rowspan, cols[ i ].group);
					if (l > ml) {
						ml = l;
					}
				} else if (rowspan > 0) {
					// M.H. 8 Aug 2012 Fix for bug #118599
					l = level + rowspan - 1;
					if (l > ml) {
						ml = l;
					}
				}
			}
			return ml;
		},
		/* the second argument specifies whether data will be appended or prepended. the default is prepend. */
		_headerInit: function (tr, colgroup, prepend) {
			// different features that require extra cells such as group by, may register callbacks here
			// that will be called whenever a feature like filter row or add new row renders its logic
			var i;
			for (i = 0; i < this._headerInitCallbacks.length; i++) {
				this._headerInitCallbacks[ i ].func(tr, colgroup, prepend);
			}
		},
		_footerInit: function (tr, colgroup, prepend, cssClass) {
			// different features that require extra cells such as group by, may register callbacks here
			// that will be called whenever a feature like filter row or add new row renders its logic
			var i;
			for (i = 0; i < this._footerInitCallbacks.length; i++) {
				this._footerInitCallbacks[ i ].func(tr, colgroup, prepend, cssClass);
			}
		},
		id: function () {
			/* returns the ID of the TABLE element where data records are rendered
				returnType="string"
			*/
			return this.element[ 0 ].id;
		},
		/* returns the grid caption element (above the headers table) */
		_caption: function () {
			return this.container().find("caption");
		},
		_rootContainer: function () {
			var rootElement;
			if (!this._rContainer || this._rContainer.length === 0) {
				rootElement = this.element.closest(".ui-iggrid-root");
				this._rContainer = rootElement.length === 1 ?
					rootElement.data("igGrid").container() : this.container();
			}
			return this._rContainer;
		},
		container: function () {
			/* returns the DIV that is the topmost container of the grid widget
				returnType="dom"
			*/
			/* if (this._isWrapped === true) {
				return this.element;
			} else {
			return $('#' + this.element[0].id + '_container'); */
			if (!this._container || this._container.length === 0) {
				this._container = this.element.closest("div[id=" + this.id() + "_container]");
			}
			return this._container;
		},
		headersTable: function () {
			/* returns the table that contains the header cells
				returnType="dom"
			*/
			if (this.options.fixedHeaders === true && this.options.height !== null) {
				//return $('#' + this.element[0].id + '_headers');
				return this.container().find("#" + this.id() + "_headers");
			}
			return this.element;
		},
		footersTable: function () {
			/* returns the table that contains the footer cells
				returnType="dom"
			*/
			if (this.options.fixedFooters === true && this.options.height !== null) {
				//return $('#' + this.element[0].id + '_footers');
				return this.container().find("#" + this.id() + "_footers");
			}
			return this.element;
		},
		scrollContainer: function () {
			/* returns the DIV that is used as a scroll container for the grid contents
				returnType="dom"
			*/
			/* return $('#' + this.element[0].id + '_scroll'); */
			return this.container().children("#" + this.id() + "_scroll");
		},
		fixedContainer: function () {
			/* returns the DIV that is the topmost container of the fixed grid - contains fixed columns(in ColumnFixing scenario)
				returnType="dom"
			*/
			return this.container().find("#" + this.id() + "_mainFixedContainer");
		},
		fixedBodyContainer: function () {
			/* returns the DIV that is the topmost container of the fixed body grid - contains fixed columns(in ColumnFixing scenario)
				returnType="dom"
			*/
			return this.container().find("#" + this.id() + "_fixedBodyContainer");
		},
		fixedFooterContainer: function () {
			/* returns container(jQuery representation) containing fixed footer - contains fixed columns(in ColumnFixing scenario)
				returnType="object" jQuery representation of fixed table
			*/
			return this.container().find("#" + this.id() + "_fixedFooterContainer");
		},
		fixedHeaderContainer: function () {
			/* returns container(jQuery representation) containing fixed header - contains fixed columns(in ColumnFixing scenario)
				returnType="object" jQuery representation of fixed table
			*/
			return this.container().find("#" + this.id() + "_fixedHeaderContainer");
		},
		fixedHeadersTable: function () {
			/* returns the table that contains the FIXED header cells - contains fixed columns(in ColumnFixing scenario)
				returnType="dom"
			*/
			if (this.options.fixedHeaders === true && this.options.height !== null) {
				return this.container().find("#" + this.id() + "_headers_fixed");
			}
			return this.container().find("#" + this.id() + "_fixed");
		},
		fixedFootersTable: function () {
			/* returns the table that contains the footer cells - contains fixed columns(in ColumnFixing scenario)
				returnType="dom"
			*/
			if (this.options.fixedFooters === true && this.options.height !== null) {
				return this.container().find("#" + this.id() + "_footers_fixed");
			}
			return this.container().find("#" + this.id() + "_fixed");
		},
		_vdisplaycontainer: function () {
			return this.container().find("#" + this.id() + "_displayContainer");
		},
		_virtualcontainer: function () {
			return this.container().find("#" + this.id() + "_virtualContainer");
		},
		_vhorizontalcontainer: function () {
			return this.container().find("#" + this.id() + "_horizontalScrollContainer");
		},
		_fixedfooters: function () {
			return this.container().find("#" + this.id() + "_footer_container");
		},
		/* Accepts parameters:
		x - column index
		y - row index
		isFixed - defines whether to get cell from fixed table */
		cellAt: function (x, y, isFixed) {
			/* returns the cell TD element at the specified location
				paramType="number" The column index.
				paramType="number" The row index.
				paramType="bool" Optional parameter - if true get cell TD at the specified location from the fixed table
				returnType="dom" The cell at (x, y).
			*/
			var i, row, colKey, recDataIndex;
			/* returns a cell at the specified location in the table. jQuery selectors are not used for performance reasons. */
			if (x === undefined || y === undefined) {
				return null;
			}
			/* return this.element.find('tbody tr:nth-child(' + (y + 1) + ') td:nth-child(' + (x + 1) + ')'); */
			if (this.table === undefined) {
				this.table = this.element[ 0 ];
			}

			if (this._isMultiRowGrid()) {
				colKey = this._rlm[ y % this._recordVerticalSize() ][ x ];
				recDataIndex = parseInt(y / this._recordVerticalSize(), 10);
				return this._getRowsByRecordIndex(recDataIndex)
					.find("td[aria-describedby ='" + this.id() + "_" + colKey + "']")[ 0 ];
			}

			/* A.T. 21 Jan 2011 - We must account for all THEAD and TFOOT
			rows ! . Calculate that only once, for perf. reasons */
			i = this._dataRowIndex(y);
			if (isFixed === true) {
				if (this._fixedTable === undefined) {
					this._fixedTable = this.element;
				}
				return this._fixedTable[ 0 ].rows[ i ].cells[ x ];
			}
			row = this.table.rows[ i ];
			if (!row) {
				return null;
			}
			return row.cells[ x ];
		},
		cellById: function (rowId, columnKey) {
			/* returns the cell TD element by row id and column key
				paramType="number|string" The id of the row.
				paramType="string" The column key.
				returnType="dom" The cell for (rowId, columnKey).
			*/
			var colIndex, i, rows, isFixed = this.isFixedColumn(columnKey);
			if (_aNull(rowId) || _aNull(columnKey)) {
				return null;
			}
			if (this.table === undefined) {
				this.table = this.element[ 0 ];
			}

			if (isFixed) {
				rows = this.fixedBodyContainer().find("tbody")
					.first().children("[data-id='" + rowId + "']");
			} else {
				rows = this.element.children("tbody")
					.children("[data-id='" + rowId + "']");
			}

			return rows.children("td[aria-describedby = '" + this.id() + "_" + columnKey + "']");
		},
		/* Returns fixed table */
		fixedTable: function () {
			/* returns the fixed table - contains fixed columns(in ColumnFixing scenario). If there aren't fixed columns returns the grid table
				returnType="object" jQuery representation of fixed table
			*/
			if (this._fixedTable === undefined) {
				this._fixedTable = this.element;
			}
			return this._fixedTable;
		},
		_calculateHeaderFooterRows: function () {
			// A.T. 29 Aug 2011 - this is recursive and needs to be reworked
			//return this.element.find('thead tr').length + this.element.find('tfoot tr').length;
			var theadCount = 0, tfootCount = 0;
			theadCount = this.element.children("thead").children("tr").length;
			/* M.H. 11 Oct. 2011 Fix for bug #90863 - footer  rows count should not be calculated */
			/* M.H. 27 April 2012 Fix for bug #105427 - Opera counts tfoot rows then tbody tr */
			if ($.ig.util.isOpera) {
				tfootCount = this.element.children("tfoot").children("tr").length;
			}
			return theadCount + tfootCount;
		},
		_dataRowIndex: function (i) {
			var extrahr, j;
			if (this.table === undefined) {
				this.table = this.element[ 0 ];
			}
			if (this._additionalTrCount === undefined || this._additionalTrCount === null) {
				this._additionalTrCount = this._calculateHeaderFooterRows();
			}
			/* get all data-container rows and exclude them */
			if (this._hc === true) {
				extrahr = this.element.children("tbody").children("tr[data-container]");
				for (j = 0; j < extrahr.length; j++) {
					if ($(extrahr[ j ]).index() <= i) {
						i += 1;
					}
				}
			}
			i = i + this._additionalTrCount;
			if (i >= this.table.rows.length) {
				i = this.table.rows.length - 1;
			} else if (i < 0) {
				i = 0;
			}
			return i;
		},
		immediateChildrenWidgets: function () {
			/* gets all immediate igGrid children of the current grid
				returnType="array"
			*/
			return this.element.children("tbody").children("tr")
				.children(".ui-iggrid-childarea").children("div").children("div").map(function () {
					if ($(this).children(".ui-iggrid-scrolldiv").length > 0) {
						return $(this).children(".ui-iggrid-scrolldiv")
							.children(".ui-iggrid-table").data("igGrid");
					}
					return $(this).children(".ui-iggrid-table").data("igGrid");
				});
		},
		childrenWidgets: function () {
			/* gets all igGrid children of the current grid, recursively
				returnType="array"
			*/
			return this.element.find("tbody > tr > .ui-iggrid-childarea")
				.find(".ui-iggrid-table").map(function () {
					return $(this).data("igGrid");
				});
		},
		children: function () {
			/* gets all igGrid children's elements of the current grid, recursively
				returnType="array"
			*/
			return this.element.find("tbody > tr > .ui-iggrid-childarea").find(".ui-iggrid-table");
		},
		immediateChildren: function () {
			/* gets all immediate igGrid children's elements of the current grid
				returnType="array"
			*/
			return this.element
				.children("tbody")
				.children("tr")
				.children(".ui-iggrid-childarea")
				.children("div")
				.children("div")
				.map(function () {
					if ($(this).children(".ui-iggrid-scrolldiv").length > 0) {
						return $(this).children(".ui-iggrid-scrolldiv").children(".ui-iggrid-table");
					}
					return $(this).children(".ui-iggrid-table");
				});
		},
		/* Accepts parameters:
		i - row index */
		rowAt: function (i) {
			/* returns the row (TR element) at the specified index. jQuery selectors aren't used for performance reasons
				paramType="number" The row index.
				returnType="dom" the row at the specified index
			*/
			/* A.T. 21 Jan 2011 - We must account for all THEAD and TFOOT rows ! .
			Calculate that only once, for perf. reasons */
			i = this._dataRowIndex(i);
			return this.table.rows[ i ];
		},
		rowById: function (rowId, isFixed) {
			/* returns the row TR element by row id
				paramType="number|string" The id of the row.
				paramType="bool" optional="true" Specify search in the fixed container.
				returnType="dom" The row for (rowId).
			*/
			if (_aNull(rowId)) {
				return null;
			}
			if (isFixed) {
				return this.fixedBodyContainer().find("tbody")
					.first().children("[data-id='" + rowId + "']");
			}
			return this.element.children("tbody").children("[data-id='" + rowId + "']");
		},
		/* Accepts parameters:
		i - row index */
		fixedRowAt: function (i) {
			/* returns the fixed row (TR element) at the specified index. jQuery selectors aren't used for performance reasons(in ColumnFixing scenario - only when there is at least one fixed column)
				paramType="number" The row index.
				returnType="dom" the row at the specified index
			*/
			/* M.H. 22 Aug 2013 Fix for bug #145764: The rowAt method returns
			only the unfixed row when there is fixed columns. */
			var rows = this.fixedBodyContainer().find("tbody>tr");
			if (rows.length > 0) {
				return rows[ i ];
			}
			return null;
		},
		fixedRows: function () {
			/* returns a list of all fixed TR elements holding data in the grid(in ColumnFixing scenario - only when there is at least one fixed column)
				returnType="array"
			*/
			/*  M.H. 23 Jul 2013 Fix for bug #143616: The row element that is
			returned by activeRow method is only from the unfixed area. */
			return this.fixedBodyContainer().find("tbody>tr");
		},
		rows: function () {
			/* returns a list of all TR elements holding data in the grid(when there is at least one fixed column returns rows only in the UNFIXED table)
				returnType="array"
			*/
			/* return this.element.find('tbody tr'); */
			return this.element.children("tbody").children("tr");
		},
		allFixedRows: function () {
			/* returns all data fixed rows recursively, not only the immediate ones(in ColumnFixing scenario - only when there is at least one fixed column)
				returnType="array"
			*/
			/* M.H. 23 Jul 2013 Fix for bug #143616: The row element that is
			returned by activeRow method is only from the unfixed area. */
			return this.fixedBodyContainer().find("tbody tr");
		},
		allRows: function () {
			/* returns all data rows recursively, not only the immediate ones(when there is at least one fixed column returns rows only in the UNFIXED table)
				returnType="array"
			*/
			return this.element.find("tbody tr");
		},
		_getColumnInfo: function (id, cols) {
			// if id is specified searches for a column with the specified id - the result is object containing 2 properties - column and index in the collection
			// if cols is not specified it is taken grid._oldCols - array of MCH column
			var aCols = cols || this._oldCols || this.options.columns, i;
			for (i = 0; i < aCols.length; i++) {
				if (aCols[ i ].key === id || aCols[ i ].identifier === id) {
					return { column: aCols[ i ], index: i };
				}
			}
			return null;
		},
		columnByKey: function (key) {
			/* returns a column object by the specified column key
				paramType="string" The column key.
				returnType="object" a column definition
			*/
			var cols = this.options.columns, i;
			for (i = 0; i < cols.length; i++) {
				/* L.A. 07 December 2012 - Fixing bug #129117 When you use DOM table as a data
				source for the grid and redefine the header of a column in columnSettings it
				duplicates the column with the new header text.
				Features like column moving, sorting and etc are passing key as string */
				if (String(cols[ i ].key) === String(key)) {
					return cols[ i ];
				}
			}
			return null;
		},
		columnByText: function (text) {
			/* Returns a column object by the specified header text. If there are multiple matches, returns the first one.
				paramType="string" The column header text.
				returnType="object" a column definition
			*/
			var cols = this.options.columns, i;
			for (i = 0; i < cols.length; i++) {
				if (cols[ i ].headerText === text) {
					return cols[ i ];
				}
			}
			return null;
		},
		selectedCells: function () {
			/* returns an array of selected cells in arbitrary order where every objects has the format { element: , row: , index: , rowIndex: , columnKey: } .
				If multiple selection is disabled the function will return null.
				returnType="array"
			*/
			if (this._selection.settings.owner !== this) {
				return [];
			}
			return this._selection.settings.multipleSelection ? this._selection.selectedCells() : null;
		},
		selectedRows: function () {
			/* returns an array of selected rows in arbitrary order where every object has the format { element: , index: } .
				If multiple selection is disabled the function will return null.
				returnType="array"
			*/
			if (this._selection.settings.owner !== this) {
				return [];
			}
			return this._selection.settings.multipleSelection ? this._selection.selectedRows() : null;
		},
		selectedCell: function () {
			/* returns the currently selected cell that has the format { element: , row: , index: , rowIndex: , columnKey: }, if any.
				If multiple selection is enabled the function will return null.
				returnType="object"
			*/
			var selectedCells;
			if (this._selection.settings.owner !== this) {
				return null;
			}
			selectedCells = this._selection.selectedCells();
			return this._selection.settings.multipleSelection ?
				null : (selectedCells.length === 1 ? selectedCells[ 0 ] : null);
		},
		selectedRow: function () {
			/* returns the currently selected row that has the format { element: , index: }, if any.
				If multiple selection is enabled the function will return null.
				returnType="object"
			*/
			var selectedRows;
			if (this._selection.settings.owner !== this) {
				return null;
			}
			selectedRows = this._selection.selectedRows();
			return this._selection.settings.multipleSelection ?
				null : (selectedRows.length === 1 ? selectedRows[ 0 ] : null);
		},
		activeCell: function () {
			/* returns the currently active (focused) cell that has the format { element: , row: , index: , rowIndex: , columnKey: }, if any.
				returnType="object"
			*/
			if (this._selection instanceof $.ig.SelectedRowsCollection) {
				return null;
			}
			if (this._selection.settings.owner !== this) {
				return null;
			}
			return this._selection.activeCell();
		},
		activeRow: function () {
			/* returns the currently active (focused) row that has the format { element: , index: }, if any.
				returnType="object"
			*/
			if (this._selection instanceof $.ig.SelectedCellsCollection) {
				return null;
			}
			if (this._selection.settings.owner !== this) {
				return null;
			}
			return this._selection.activeRow();
		},
		getCellValue: function (rowId, colKey) {
			/* Retrieves a cell value using the row index and the column key. If a primaryKey is defined, rowId is assumed to be the row Key (not index).
				If primary key is not defined, then rowId is converted to a number and is used as a row index.
				paramType="object" Row index or row key (primary key).
				paramType="string" The column key.
				returnType="object" The corresponding cell value.
			*/
			var id = parseInt(rowId, 10), i, cols = this.options.columns,
				colFound = false, rec, primaryKeyCol, tx;
			/* check the case where we have a local transaction log,
			and there is a corresponding entry for that row"s cell */
			tx = this.dataSource.pendingTransactions();
			if (this.options.autoCommit === false && tx.length > 0) {
				for (i = 0; i < tx.length; i++) {
					if (tx[ i ].rowId === rowId) {
						if (tx[ i ].type === "cell" && tx[ i ].col === colKey) {
							rec = $.extend(true, {}, this.dataSource.findRecordByKey(rowId));
							rec[ colKey ] = tx[ i ].value;
							return this.dataSource.getCellValue(colKey, rec);
						}
						if (tx[ i ].type === "row" || tx[ i ].type === "newrow") {
							return this.dataSource.getCellValue(colKey, tx[ i ].row);
						}
					}
				}
			}
			/* M.H. 6 Jan 2014 Fix for bug #160364: noSuchColumnDefined error is not
			thrown when an undefined column is used in getCellValue method */
			if ($.type(colKey) === "string") {
				for (i = 0; i < cols.length; i++) {
					if (cols[ i ].key === colKey) {
						colFound = true;
						break;
					}
				}
				if (colFound === false) {
					throw new Error($.ig.Grid.locale.noSuchColumnDefined);
				}
			}
			/* check for primary key */
			if (this.options.primaryKey !== null) {
				// assume rowId is the primary Key, not the row index
				primaryKeyCol = this.columnByKey(this.options.primaryKey);
				if (primaryKeyCol.dataType === "number" || primaryKeyCol.dataType === "numeric") {
					rec = this.dataSource.findRecordByKey(parseInt(rowId, 10));
				} else {
					rec = this.dataSource.findRecordByKey(rowId);
				}
				if (rec === null || rec === undefined) {
					throw new Error($.ig.Grid.locale.recordNotFound.replace("{id}", rowId));
				}
				return this.dataSource.getCellValue(colKey, rec);
			}
			/* validate */
			if (id >= this.dataSource.dataView().length) {
				throw new Error($.ig.Grid.locale.indexOutOfRange
					.replace("{max}", this.dataSource.dataView().length));
			}
			if ($.type(colKey) === "string") {
				//	return this.dataSource.dataView()[id][colKey];
				return this.dataSource.getCellValue(colKey, this.dataSource.dataView()[ id ]);
			}
			if (cols.length <= colKey) {
				throw new Error($.ig.Grid.locale.columnIndexOutOfRange.replace("{max}", cols.length));
			}
			return this.dataSource.getCellValue(colKey, this.dataSource.dataView()[ id ]);
		},
		getCellText: function (rowId, colKey) {
			/* Returns the cell text. If colKey is a number, the index of the column is used (instead of a column name)- does not apply when using a Multi-Row Layout grid.
				This is the actual text (or HTML string) for the contents of the cell.
				paramType="object" Row index or row data key (primary key)
				paramType="string" Column key.
				returnType="string" the cell text for the respective cell
			*/
			var colIndex;
			if ($.type(colKey) === "string") {
				if (this.columnByKey(colKey) === null) {
					throw new Error($.ig.Grid.locale.columnNotFound.replace("{key}", colKey));
				}

				if (this.options.primaryKey !== null) {
					// find the TR using a selector
					return this.element.find("tr[data-id='" + rowId +
						"']>td[aria-describedBy='" + this.id() + "_" + colKey + "']").text();
			} else {
					return this._getRowsByRecordIndex(rowId)
						.find("td[aria-describedBy='" + this.id() + "_" + colKey + "']").text();
				}

			} else {
				if (this._isMultiRowGrid()) {
					return null;
				}
				colIndex = colKey;
			}
			if (colIndex === undefined) {
				throw new Error($.ig.Grid.locale.columnNotFound.replace("{key}", colKey));
			}
			/* use with care. */
			if (this.options.primaryKey !== null) {
				// find the TR using a selector
				return this.element.find("tr[data-id='" + rowId +
					"']>td:nth-child(" + (colIndex + 1) + ")").text();
			}
			return $(this.cellAt(colIndex, parseInt(rowId, 10))).text();
		},
		setColumnTemplate: function (col, tmpl, render) {
			/* Sets a new template for a column after initialization and renders the grid if not explicitly disabled. This method will replace any existing explicitly set row template and will build one anew from the column ones.
				paramType="string|number" An identifier of the column to set template for (index or key)
				paramType="string" The column template to set
				paramType="bool" optional="true" default="true" Should the grid rerender after template is set
			*/
			var colIdx = this._getColIdxById(this.options.columns, col);
			if (colIdx === null || colIdx === undefined) {
				return;
			}
			col = this.options.columns[ colIdx ];
			col.template = tmpl;
			render = render === null || render === undefined ? true : render;
			if (render && !col.hidden) {
				this._renderData();
			}
		},
		commit: function (rowId) {
			/* Commits all pending transactions to the client data source. Note that there won't be anything to commit on the UI, since it is updated instantly. In order to rollback the actual UI, a call to dataBind() is required.
				paramType="string|number" optional="true" If specified, will commit only that transaction corresponding to the specified record key.
			*/
			var key = this._normalizedKey(rowId), row, idx;
			/* commits all changes to the data source (delegates to igDataSource).
			Note that there won't be anything to commit on the UI, since it is updated instantly !
			in order to rollback the actual UI, a call to dataBind() is required ! */
			this.dataSource.commit(key);
			if (this._fireInternalEvent("_gridCommit")) {
				// if a feature handles the commit call we shouldn't redo the same
				return;
			}
			this._isToSetUnboundColumns = true;
			if (this.options.virtualization ||
				this.options.rowVirtualization ||
				this.options.columnVirtualization) {
				if (this.options.virtualizationMode === "continuous") {
					this._renderVirtualRecords();
				} else {
					this._buildVirtualDom();
					this.virtualScrollTo(this._startRowIndex);
				}
			} else if (key !== null) {
				row = this.rowById(key);
				if (row && row.length) {
					if (row.hasClass(this.css.deletedRecord)) {
						idx = row.index();
						row.remove();
						this._reapplyZebraStyle(idx);
					} else {
						row.removeClass(this.css.modifiedRecord);
					}
				}
			} else {
				this._renderData();
			}
		},
		rollback: function (rowId, updateUI) {
			/* Clears the transaction log (delegates to igDataSource). Note that this does not update the UI. In case the UI must be updated, set the second parameter "updateUI" to true, which will trigger a call to dataBind() to re-render the contents.
				paramType="string|number" optional="true" If specified, will only rollback the transactions with that row id.
				paramType="bool" optional="true" Whether to update the UI or not.
				returnType="array" The transactions that have been rolled back.
			*/
			var key = this._normalizedKey(rowId), transactions = this.dataSource.rollback(key),
				i, funcUpdateUI, fCols = this.hasFixedColumns(), grid = this;
			if (updateUI === true) {
				funcUpdateUI = function (transaction, fixed) {
					var $tbl = fixed ? grid.fixedTable() : grid.element,
						tr = $tbl.find("tr[data-id='" + transaction.rowId + "']"),
						content, tridx, rec, td, index, col;
					switch (transaction.type) {
						case "newrow":
							/* the row found needs to be removed */
							tridx = grid.element.children("tbody")
								.children("tr:not([data-container],[data-grouprow])").index(tr);
							tr.remove();
							grid._reapplyZebraStyle(tridx);
							break;
						case "deleterow":
							/* the row found should have its deleted style cleared */
							tr.removeClass(grid.css.deletedRecord);
							break;
						case "cell":
							/* all cell transactions for this row will have the same row id and therefore
							will be removed together so we'll just remove the whole row's modified style */
							col = grid.columnByKey(transaction.col);
							tr.removeClass(grid.css.modifiedRecord);
							td = grid.cellById(transaction.rowId, transaction.col);
							rec = grid.dataSource.findRecordByKey(transaction.rowId);
							if (col.template && col.template.length) {
								content = grid._renderTemplatedCell(rec, col);
								index = content.indexOf(">");
								content = content.substring(index + 1, content.length);
								td.html(content);
							} else {
								td.html(String(grid._renderCell(rec[ transaction.col ], col, rec)));
							}
							break;
						case "row":
							tr.removeClass(grid.css.modifiedRecord);
							rec = grid.dataSource.findRecordByKey(transaction.rowId);
							grid._renderRow(rec, tr[ 0 ], rec[ grid.options.primaryKey ]);
							break;
					}
				};
				if (!_aNull(rowId)) {
					if (transactions.length === 0) {
						// if no transactions are returned by the data source we shouldn't do anything
						return;
					}
					i = transactions.length;
					while (i-- > 0) {
						funcUpdateUI(transactions[ i ]);
						if (fCols) {
							funcUpdateUI(transactions[ i ], true);
						}
					}
					/* M.H. 8 Sep 2016 Fix for bug 223810: UI in fixed columns is not updated after rollback is called with updateUI=true */
					this._fireInternalEvent("_rollbackApplied");
				} else {
					this.dataBind();
				}
				return transactions;
			}
		},
		findRecordByKey: function (key) {
			/* returns a record by a specified key (requires that primaryKey is set in the settings).
				That is a wrapper for this.dataSource.findRecordByKey(key).
				paramType="string" Primary key of the record
				returnType="object" a JavaScript object specifying the found record, or null if no record is found
			*/
			return this.dataSource.findRecordByKey(key);
		},
		getDetachedRecord: function (t) {
			/* Returns a standalone object (copy) that represents the committed transactions, but detached from the data source.
				That is a wrapper for this.dataSource.getDetachedRecord(t).
				paramType="object" A transaction object.
				returnType="object" A copy of a record from the data source.
			*/
			return this.dataSource.getDetachedRecord(t);
		},
		pendingTransactions: function () {
			/* Returns a list of all transaction objects that are pending to be committed or rolled back to the data source.
				That is a wrapper for this.dataSource.pendingTransactions().
				returnType="array"
			*/
			return this.dataSource.pendingTransactions();
		},
		allTransactions: function () {
			/* returns a list of all transaction objects that are either pending, or have been committed in the data source.
				That is a wrapper for this.dataSource.allTransactions().
				returnType="array"
			*/
			return this.dataSource.allTransactions();
		},
		transactionsAsString: function () {
			/* Returns the accumulated transaction log as a string. The purpose of this is to be passed to URLs or used conveniently.
				That is a wrapper for this.dataSource.transactionsAsString().
				returnType="string"
			*/
			return this.dataSource.transactionsAsString();
		},
		_normalizedKey: function (id) {
			// returns a normalized key because id can be both index or a primary key (string / number)
			var key, primaryKeyCol;
			/* A.T. 15 Sept. 2011 - Fix for #88104 */
			if (id === undefined || id === null) {
				return null;
			}
			key = id;
			if (this.options.primaryKey !== null) {
				primaryKeyCol = this.columnByKey(this.options.primaryKey);
				if (primaryKeyCol.dataType === "number" || primaryKeyCol.dataType === "numeric") {
					key = parseInt(id, 10);
				}
			} else {
				key = parseInt(id, 10);
			}
			return key;
		},
		saveChanges: function (success, error) {
			/* Invokes an AJAX request to the updateUrl option (if specified) and passes the serialized transaction log (a serialized JSON string) as part of the POST request.
			paramType="function" Specifies a custom function to be called when AJAX request to the updateUrl option succeeds(optional)
			paramType="function" Specifies a custom function to be called when AJAX request to the updateUrl option fails(optional)
			*/
			this.dataSource.saveChanges(success, error);
		},
		/* if rowId is not null, then it is a request to fix css (update/add/delete row) */
		_renderRow: function (rec, tr) {
			var i, tds, td, cols, col, cs, cl, content,
				cv = this._isColumnVirtualizationEnabled();
			tr = $(tr);
			/* data cells */
			tds = tr.children("td:not([data-skip='true'],[data-parent])");
			cols = this._visibleColumns();
			cs = this._startColIndex || 0;
			cl = cv ? this._virtualColumnCount + cs : cols.length;
			for (i = cs; i < cl; i++) {
				col = cols[ i ];
				td = tds.filter("[aria-describedby='" + this.id() + "_" + col.key + "']");
				/* K.D. Bug #102873 Rendering branch is done here */
				if (col.template && col.template.length) {
					content = this._renderTemplatedCell(rec, col);
					if (content.indexOf("<td") === 0) {
						td.html($(content).html());
					} else {
						td.html(content);
					}
				} else {
					td.html(String(this._renderCell(rec[ col.key ], col, rec)));
				}
			}
			return tr;
		},
		renderNewRow: function (rec) {
			/* Adds a new row (TR) to the grid, by taking a data row object. Assumes the record will have the primary key.
				paramType="object" The data row JavaScript object.
				paramType="string" optional="true" Identifier/key of row. If missing, then number of rows in grid is used.
			*/
			var tbody = this.element.children("tbody"), index, self = this,
				virt = this.options.virtualization === true ||
				this.options.rowVirtualization === true,
				fv = this.options.virtualizationMode === "fixed";
			/* S.S. April 25, 2013 Bug #139544 When virtualization is enabled we need
			to rebuild the dom so that all virtualization related variables are properly set */
			if (virt) {
				this._trigger("virtualrendering");
				if (fv) {
					this._buildVirtualDom();
				} else {
					this._renderVirtualRecordsContinuous();
					this._startRowIndex = 0;
				}
				this.virtualScrollTo(this._totalRowCount);
			} else {
				index = tbody.children("[data-container!=\"true\"]").length;
				/* K.D. April 5th, 2012 Bug #107910 Render record handles the
				rendering of regular and templated records. */
				MSApp.execUnsafeLocalFunction(function () {
					if (self._isMultiRowGrid()) {
						tbody.append(self._renderRecordFromLayout(rec, index));
					} else {
					tbody.append(self._renderRecord(rec, index));
					}
				});
			}
		},
		_findTableRowByKey: function (key) {
			var primaryKeyIndex, cols = this.options.columns, r, i;
			/* find the index of the primary key column */
			if (this.options.primaryKey !== null) {
				for (i = 0; i < cols.length; i++) {
					if (cols[ i ].key === this.options.primaryKey) {
						primaryKeyIndex = i;
						break;
					}
				}
				if (primaryKeyIndex === undefined) {
					throw new Error($.ig.Grid.locale.columnNotFound.replace("{key}", this.options.primaryKey));
				}
				r = this.element.find("td:nth-child('" +
					(primaryKeyIndex + 1) + "'):contains('" + key + "')").parent();
				return r.length === 0 ? null : r[ 0 ];
			}
			return this.rowAt(parseInt(key, 10));
		},
		/* A.T. 21 Jan 2010 - Fix for bug #62277 - Data rebinding doesn't work properly. You have to reset all the properties
		for the binding widget options are deep cloned !!! Therefore if the data source is LOCAL json array or some object,
		it must be set through the API, not in the options. Alternatively if it is set from options, it will be deep copied ! */
		dataSourceObject: function (dataSource) {
			/* if the data source points to a local JSON array of data, and it is necessary to reset it at runtime, it must be done through this API member instead of the options (options.dataSource)
				paramType="object" New data source object.
			*/
			if (dataSource !== undefined) {
				this.options.dataSource = dataSource;
			} else {
				return this.options.dataSource;
			}
		},
		totalRecordsCount: function () {
			/* Returns the total number of records in the underlying backend. If paging or filtering is enabled, this may differ from the number of records in the client-side data source.
				In order for this to work, the response JSON/XML must include a property that specifies the total number of records, which name is specified by options.responseTotalRecCountKey.
				This functionality is completely delegated to the data source control.
				returnType="number" total number of records in the backend
			*/
			return this.dataSource.totalRecordsCount();
		},
		_wrapElementDiv: function () {
			this._isWrapped = true;
			this.element = $("<table role='grid'></table>")
				.appendTo(this.element).attr("id", this.id() + "_table");
			this.element.data("igGrid", this);
		},
		dataBind: function (internal) {
			/* causes the grid to data bind to the data source (local or remote) , and re-render all of the data as well
			excluded="true"
			*/
			var dataOptions, i, noCancel = true, noCancelRendering = true, customFunc, dataSource;
			/* M.H. 17 May 2012 Fix for bug #111100: The rendering event has to be fire
			before the (dataBinding and dataBound) pair of events */
			/* A.T. 9 Jan 2012 - fix for bug #98777 */
			if (!this._initialized) {
				noCancelRendering = this._trigger(this.events.rendering, null, { owner: this });
			}
			if (noCancelRendering) {
				dataOptions = this._generateDataSourceOptions(this.options);
				/* A.T. 18 Jan 2011 - Fix for bug #62277 - Data rebinding doesn't work properly.
				You have to reset all the properties for the binding */
				dataSource = this._createDataSource(dataOptions);
				noCancel = this._trigger(this.events.dataBinding, null,
					{ owner: this, dataSource: dataSource });
				if (internal === undefined) {
					this.options.requiresDataBinding = true;
				}
				if (noCancel) {
					if (this.options.requiresDataBinding) {
						/* M.H. 23 Jan 2013 Fix for bug #130586 */
						if (this._hasUnboundColumns) {
							this._rebindUnboundColumns = true;
						}
						/* initialize grid features and attach their event handlers */
						this._dataOptions = dataOptions;
						this.dataSource = dataSource;
						/* if (this.options.autoGenerateColumns === false) { */
						/* M.H. Implement task 166872: Remove the tight coupling between igDataSource and igGrid */
						if (this.dataSource) {
							/* we should cache the original custom convert
							function(from this.dataSource.settings.sorting.customConvertFunc) if it is set, because we re-set it */
							customFunc = this.dataSource.settings.sorting.customConvertFunc;
							this.dataSource.settings.sorting.customConvertFunc =
								$.proxy(this._convertSortingDataSourceValue, this);
							/* if this.dataSource.settings.sorting.customConvertFunc is already re-set -
							we should ensure that this._oSortingCustomConvertFunc(original value) will not point to the same function */
							if (customFunc && (customFunc.guid === undefined ||
								customFunc.guid !== this.dataSource.settings.sorting.customConvertFunc.guid)) {
								/* we should cache the original function(if set from the dataSource) and call it from our handler */
								this._oSortingCustomConvertFunc = customFunc;
							}
						}
						if (!this._initialized) {
							// check if the element passed on the widget is of type table or div
							if (this.element.is("div")) {
								this._wrapElementDiv();
								/* gridElement = this.element[0]; */
							}
							for (i = 0; i < this.options.features.length; i++) {
								this._initFeature(this.options.features[ i ], dataOptions);
							}
							/*NOTE: this is the only point were we can do the capturing of the
							hidden columns as it should be before we render the grid
							and after all features(including hiding) have been initialized */
							/* A.Y. bug 98100. In the case of autogenerated columns this will be done later. */
							if (this.options.autoGenerateColumns !== true) {
								this._captureInitiallyHiddenColumns();
							}
							this._visibleColumnsArray = undefined;
						} else {
							// M.H. 26 Mar 2013 Fix for bug #126556: DataBound event is fired twice after GroupBy when explicitly invoking "dataBind" method
							this._isDataBoundCalled = true;
							this.element.trigger("iggriduidirty", { owner: this });
							/* fire UI State dirty so that features reset their UI (without destroying them) */
							for (i = 0; i < this.options.features.length; i++) {
								this._initFeatureSettings(this.options.features[ i ]);
							}
							/* M.H. 9 Nov 2012 Fix for bug #126509 */
							/* this._trigger("headerExtraCellsModified", null, { owner: this }); */
						}
						this._renderGrid();
						if (this._loadingIndicator === undefined) {
							this._initLoadingIndicator();
						}
						if (this._loadingIndicator) {
							this._loadingIndicator.show();
						}
						this.dataSource.dataBind();
						this.options.requiresDataBinding = false;
					} else {
						// data source is already bound
						this._renderGrid();
					}
				}
				/* M.H. 17 May 2012 Fix for bug #111100 */
			} else {
				// cancel render data
				this._cancelRendering = true;
			}
		},
		_mergeUnboundValues: function () {
			// merge unbound values when datasource is remote and merguUnboundColumns is false
			var i, primaryKeyCol, metadataUC, rec, ucLength, primaryKeyColIsNumber,
				col, schema, type, dataLength, data, key, val, j,
				pk = this.options.primaryKey,
				metadata = this.dataSource.metadata("unboundValues"),
				self = this,
				hasPrimaryKey = (pk !== null && pk !== undefined),
				metaDataMergeFunction;
			if (metadata === undefined || metadata === null || metadata.length === 0 ||
					!this._unboundColumns) {
				return;
			}
			/* M.H. 30 Aug 2012 Fix for bug #120134 */
			if (hasPrimaryKey) {
				metaDataMergeFunction = function (ind, val) {
					if (primaryKeyColIsNumber) {
						rec = self.dataSource.findRecordByKey(parseInt(ind, 10));
					} else {
						rec = self.dataSource.findRecordByKey(ind);
					}
					if (rec === null || rec === undefined) {
						return true;
					}
					/* M.H. 10 Sep 2012 Fix for bug #120724 */
					if (schema !== undefined && schema !== null) {
						// M.H. 11 Sep 2012 Fix for bug #120867
						val = schema._convertType(type, val, rec[ pk ], key);
					}
					/* M.H. 11 Sep 2012 Fix for bug #120668 */
					self._addUnboundColumnValue(key, val);
					rec[ key ] = val;
				};
				primaryKeyCol = this.columnByKey(pk);
				primaryKeyColIsNumber = (primaryKeyCol.dataType === "number");
			}
			ucLength = this._unboundColumns.length;
			schema = this.dataSource.schema();
			for (i = 0; i < ucLength; i++) {
				key = this._unboundColumns[ i ].key;
				metadataUC = metadata[ key ];
				if (metadataUC === null || metadataUC === undefined) {
					continue;
				}
				col = this.getUnboundColumnByKey(key);
				type = null;
				if (col !== null && col.dataType) {
					type = col.dataType;
				}
				/* M.H. 30 Aug 2012 Fix for bug #120134 */
				if (hasPrimaryKey) {
					// M.H. 10 Sep 2012 Fix for bug #120724
					$.each(metadataUC, metaDataMergeFunction);
				} else {
					self._renderUnboundValues(metadataUC, key);
				}
				/* M.H. 11 Sep 2012 Fix for bug #120569 */
				if (type === "bool" || type === "boolean") {
					data = this.dataSource.data();
					val = schema._convertType(type, undefined);
					dataLength = data.length;
					/* just for performance - we do not want to traverse all */
					if (dataLength <= metadataUC.length) {
						continue;
					}
					for (j = 0; j < dataLength; j++) {
						if (data[ j ][ key ] === undefined) {
							data[ j ][ key ] = val;
						}
					}
				}
			}
		},
		/* M.H. Implement task 166872: Remove the tight coupling between igDataSource and igGrid */
		_convertSortingDataSourceValue: function (val, key) {
			// Custom data value conversion function. Accepts a value and should return the converted value. It is called from the dataSource when sorting is applied. Check for reference igGridDataSource.settings.sorting.customConvertFunc
			// Apply sorting according to the applied column format(if any) - fix for bugs #130576, #118640 When the grid is bound to UTC dates
			var o = this.options, enableUTCDates = o.enableUTCDates, format,
				rowTemplate, col, dsFunc = this._oSortingCustomConvertFunc;
			if ($.type(val) === "date") {
				rowTemplate = (!o.rowTemplate || o.rowTemplate.length <= 0);
				col = this.columnByKey(key);
				if (col !== undefined && col !== null) {
					format = col.format;
				}
				if (format) {
					// L.A. 11 January 2013 - Fixing bug #130576
					// L.A. 09 August 2012 - Fixing bug #118640 When the grid is bound to UTC dates
					// (remote or local data), grouping a time-formatted date column produces incorrect groups
					if (format === "time" || format === "timeLong" || format === "h:mm:ss tt") {
						// Create date objects with fake year
						// M.H. 23 Oct 2013 Fix for bug #155639: Unable to sort date column when format is "h:mm:ss tt"
						val = new Date("January 01, 2000 " +
							$.ig.formatter(val, "date", format, rowTemplate, enableUTCDates));
					}
				}
			}
			/* if customConvertFunc from the dataSource settings is set explicitly (e.g. on init) then it should be executed
			ensure that dsFunc will not call the same function this._convertSortingDataSourceValue which will cause infinite loop */
			if ($.isFunction(dsFunc)) {
				//_convertSortingDataSourceValue is set to this.dataSource.settings.sorting.customConvertFunc via $.proxy so it has property guid
				if (dsFunc.guid === undefined ||
					dsFunc.guid !== this.dataSource.settings.sorting.customConvertFunc.guid) {
					val = dsFunc(val, key);
				}
			}
			return val;
		},
		_generateDataSourceOptions: function () {
			var schema, dataOptions, t, headers, i, instanceOfDs;
			/* if there is neither options.dataSource specified, nor options.dataSourceUrl,
			we check if we are binding to a table and if there is any existing data in it,
			and then we set the data source to that table DOM element, so that it can be
			processed by the data source control */
			if (!this.options.dataSource &&
				!this.options.dataSourceUrl &&
				this.element.is("table") &&
				this.element.find("tbody").children().length > 0) {
				this.options.dataSource = this.element[ 0 ];
			}
			/* we need to look ahead and check if the data source is a HTML Table and has column
			headers defined. in that case we need to update the headerText in the column definitions */
			if (this.options.dataSource) {
				if (this.options.dataSource.tagName && this.options.dataSource.nodeType) {
					t = $(this.options.dataSource);
				} else if ($.type(this.options.dataSource.type) === "function" &&
							this.options.dataSource.type() === "htmlTableString" &&
							$.type(this.options.dataSource.dataSource) === "function") {
					t = $(this.options.dataSource.dataSource());
				}
				if (t && t.is("table") && t.find("thead th").length > 0) {
					// generate column headers
					headers = t.find("thead tr th");
					/*jscs:disable*/
					this._tb_h = true;
					this._tb_h_arr = [];
					for (i = 0; i < headers.length; i++) {
						this._tb_h_arr.push($(headers[i]).text());
					}
					/*jscs:enable*/
					/*
					if (this.options.columns.length > 0) {
						for (i = 0; i < headers.length && i < this.options.columns.length; i++) {
							this.options.columns[i].headerText = $(headers[i]).text();
						}
					} else {
						for (i = 0; i < headers.length; i++) {
							this.options.columns.push({"headerText": $(headers[i]).text()});
						}
					}
					*/
				}
			}
			dataOptions = {
				callback: $.proxy(this._renderData, this),
				callee: this,
				responseDataKey: this.options.responseDataKey,
				responseTotalRecCountKey: this.options.responseTotalRecCountKey,
				dataSource: this.options.dataSource,
				requestType: this.options.requestType,
				responseContentType: this.options.responseContentType,
				primaryKey: this.options.primaryKey,
				localSchemaTransform: this.options.localSchemaTransform,
				autoCommit: this.options.autoCommit,
				aggregateTransactions: this.options.aggregateTransactions,
				serializeTransactionLog: this.options.serializeTransactionLog,
				updateUrl: this.options.updateUrl,
				restSettings: this.options.restSettings,
				/* M.H. 18 August 2015 Fix for bug 204834: Filter by condition:
				"Today" is not working properly when "enableUTCDates" is set to true
				if it should be applied filtering(sorting) according to universal time */
				enableUTCDates: this.options.enableUTCDates
			};
			if (this.options.dataSourceType !== null) {
				dataOptions.type = this.options.dataSourceType;
			}
			/* create a schema based on the columns definition
			iterate over the columns collection, if such exists.
			Otherwise bind to everything */
			/* A.T. 28 March 2011 - fix for bug #68548 */
			/* L.A. 02 August 2012 - Fixing bug #117263 - Grid doesn't render binding to XML string */
			/* M.H. 15 Sep 2012 Fix for bug #121429 */
			if (!this.options.dataSource ||
				!this.options.dataSource.schema ||
				!this.options.dataSource.schema() ||
				this.options.dataSource.schema()._type !== "xml") {
				schema = this._generateDataSourceSchema();
			}
			/* M.H. 27 Mar 2014 Fix for bug #168313: Grid overrides the data source schema fields
			and the localSchemaTransform option if DataSource schema is set in the DataSource(taken from options)
			and is of type DataSchema then we use it otherwise we should generate it */
			instanceOfDs = this.options.dataSource &&
				typeof this.options.dataSource._xmlToArray === "function" &&
				typeof this.options.dataSource._encodePkParams === "function";
			if ((instanceOfDs && (this.options.dataSource.settings.schema === null ||
				!this.options.dataSource.settings.schema.fields ||
				(this.options.dataSource.settings.schema.fields &&
				this.options.dataSource.settings.schema.fields.length >= 0))) ||
				!instanceOfDs) {
				dataOptions = $.extend(dataOptions, { schema: schema });
			}
			return dataOptions;
		},
		_insertUnboundColumn: function (column) {
			if (this._unboundColumns === null || this._unboundColumns === undefined) {
				this._unboundColumns = [];
			}
			this._unboundColumns.push(column);
			/* M.H. 3 Sep 2012 Fix for bug #120188 */
			if (column.key && (this._unboundValues[ column.key ] === null ||
				this._unboundValues[ column.key ] === undefined)) {
				this._unboundValues[ column.key ] = [];
			}
			/* M.H. 1 Nov 2012 Fix for bug #120553 */
			if (column.unboundValues && column.unboundValues.length > 0) {
				this._isToSetUnboundColumns = true;
			}
			this._hasUnboundColumns = true;
		},
		/* M.H. 10 Sep 2012 Fix for bug #120696 - add index value */
		_addUnboundColumnValue: function (key, value, index) {
			// insert in _unboundValues object for the unbound column with the specified key value. If index is specified at position index
			if (this._unboundValues[ key ] === null || this._unboundValues[ key ] === undefined) {
				this._unboundValues[ key ] = [];
			}
			if (index !== undefined && index !== null) {
				this._unboundValues[ key ][ index ] = value;
			} else {
				this._unboundValues[ key ].push(value);
			}
		},
		_generateDataSourceSchema: function () {
			var schema, schemaType, dsSchema, i, rec, prop, count = 0,
				cols = this.options.columns, ds = this.options.dataSource, cl, counter = 0;
			/* A.T. 22 Aug 2011 - make sure this scenario is covered as well */
			if (ds && typeof ds._xmlToArray === "function" && typeof ds._encodePkParams === "function") {
				dsSchema = ds.schema();
				/* if the dataSource has the data schema - and type of the data schema is
				different from the type of the dataSource get type of the dataSchema
				(in cases like functiondatasource) */
				/* M.H. 31 Mar 2015 Fix for bug 190451: Cannot bind grid to FunctionDataSource */
				if (dsSchema && $.type(dsSchema) === "object" &&
					typeof dsSchema.schema === "object" &&
					typeof dsSchema.isObjEmpty === "function" &&
					dsSchema._type) {
					schemaType = dsSchema._type;
					if (schemaType && schemaType !== ds.settings.type) {
						ds.settings.type = schemaType;
					}
				} else {
					dsSchema = null;
				}
				if ($.type(ds.settings.dataSource) === "array" ||
					$.type(ds.settings.dataSource) === "object") {
					ds = ds.settings.dataSource;
				} else if ($.type(ds.settings.dataSource) !== "string") {
					ds = ds.data();
				} else {
					ds = [];
				}
			}
			/* M.H. 16 May 2012 Fix for bug #99426 */
			if ($.type(ds) === "object" && this.options.responseDataKey) {
				ds = $.ig.findPath(ds, this.options.responseDataKey);
			}
			schema = {};
			schema.fields = [];
			schema.searchField = this.options.responseDataKey;
			/* M.H. 3 Dec 2014 Fix for bug #185336: Databinding a grid with an
			unbound column holding an input element creates a memory leak */
			this._unboundColumns = null;
			/* if (this.options.columns.length > 0) { */
			if (cols.length > 0 && !this.options.autoGenerateColumns) {
				// if autoGenerateColumns is true, fields for all columns in the data source must be specified
				for (i = 0; i < cols.length; i++) {
					if (cols[ i ].unbound === true || cols[ i ].unboundDS === true) {
						this._insertUnboundColumn(cols[ i ]);
						if (cols[ i ].unbound === true) {
							continue;
						}
					}
					schema.fields[ counter ] = {};
					schema.fields[ counter ].name = cols[ i ].key;
					schema.fields[ counter ].type = cols[ i ].dataType;
					schema.fields[ counter ].mapper = cols[ i ].mapper;
					counter++;
				}
				/* } else if (this.options.columns.length > 0 && this.options.autoGenerateColumns) { */
			} else if (this.options.autoGenerateColumns) {
				// A.T. Fix for #74240. Please note that in this case (if autoGenerateColumns=true, and there are custom cols defined,
				// and they have widths defined, there MUST be defaultColumnWidth specified, otherwise the remaining columns in the data source
				// will be shrinked to zero and they won't be visible !
				if (ds && ds.tagName && $(ds).is("table") &&
					$(ds).find("tbody tr").length > 0) {
					rec = $(ds).find("tbody tr")[ 0 ];
					/* count = $(rec).find('td').length; */
					$(rec).find("td").each(function () {
						if (cols.length > count) {
							schema.fields.push({
								name: cols[ count ].key || (count + 1),
								type: cols[ count ].dataType || "string"
							});
						} else {
							schema.fields.push({ name: (count + 1), type: "string" });
						}
						count++;
					});
				} else if (ds && ds.length && ds.length > 0 &&
						$.type(ds) === "array") {
					// we need to iterate through all records, since the first one may not necessarily contain any children
					//rec = this.options.dataSource[0];
					for (i = 0; i < ds.length; i++) {
						rec = ds[ i ];
						for (prop in rec) {
							if (rec.hasOwnProperty(prop)) {
								// if the column isn't already defined in the columns collection
								if (this.columnByKey(prop) === null && !this._fieldExists(prop, schema) &&
									$.type(rec[ prop ]) !== "object" && $.type(rec[ prop ]) !== "array") {
									schema.fields.push({ name: prop, type: $.ig.getColType(rec[ prop ]) });
								} else if (this.columnByKey(prop) !== null) {
									schema.fields.push({ name: prop, type: this.columnByKey(prop).dataType });
								}
								count++;
							}
						}
						/* performance improvement. The flat grid doesn't need that. Also the
						hierarchical grid doesn't need that if autoGenerateLayouts is not true. */
						if (!this.options._recurseSchema) {
							break;
						}
					}
				} else if (dsSchema && dsSchema.fields().length) {
					// when autogeneratecolumn is TRUE and it could not be taken fields from the dataSource get them from the dataSchema
					// M.H. 31 Mar 2015 Fix for bug 190451: Cannot bind grid to FunctionDataSource
					schema.fields = dsSchema.fields();
				}
				/* check for unbound columns */
				for (i = 0; i < cols.length; i++) {
					if (cols[ i ].unbound === true || cols[ i ].unboundDS === true) {
						this._insertUnboundColumn(cols[ i ]);
					}
				}
			}
			/* used in special cases where the schema needs to be additionally modified
			( as it is the case with hGrid, so that all complex objects are also added
			to the schema whenever autoGenerateColumns === false (only in that case) */
			this._trigger(
				this.events.schemaGenerated, null, { owner: this, schema: schema, dataSource: ds }
			);
			/* generate fields for child layouts if any */
			cl = this.options.columnLayouts;
			if (cl && cl.length && cl.length > 0) {
				for (i = 0; i < cl.length; i++) {
					/* M.H. 22 Apr 2014 Fix for bug #170463: A JavaScript exception is thrown when
					igHierarchicalGrid.childrenDataProperty is used instead of igHierarchicalGrid.key */
					if (cl[ i ].key === undefined) {
						continue;
					}
					schema.fields.push({ name: cl[ i ].key });
				}
			}
			return schema;
		},
		_fieldExists: function (prop, schema) {
			var i;
			for (i = 0; i < schema.fields.length; i++) {
				if (schema.fields[ i ].name === prop) {
					return true;
				}
			}
			return false;
		},
		_createDataSource: function (dataOptions) {
			var callee, dataSource;
			if (!this.options.dataSource ||
				typeof this.options.dataSource._xmlToArray !== "function" ||
				typeof this.options.dataSource._encodePkParams !== "function") {
				// fix for JSONP
				/* we use $.ig.util.isJsonpUrl to automatically detect whether to instantiate
				JSONP data source from the URL when jsonpRequest is not explicitly set */
				if ($.type(dataOptions.dataSource) === "string" &&
					(this.options.jsonpRequest || $.ig.util.isJsonpUrl(dataOptions.dataSource))) {
					dataSource = new $.ig.JSONPDataSource(dataOptions);
				} else if (this.options.restSettings.update.url !== null ||
					this.options.restSettings.update.template !== null ||
					this.options.restSettings.create.url !== null ||
					this.options.restSettings.create.template !== null ||
					this.options.restSettings.remove.url !== null ||
					this.options.restSettings.remove.template !== null) {
					dataSource = new $.ig.RESTDataSource(dataOptions);
				} else {
					dataSource = new $.ig.DataSource(dataOptions);
				}
			} else {
				dataSource = this.options.dataSource;
				/* dataOptions.dataSource = this.dataSource.settings.dataSource; */
				/* A.T. 12 Feb 2011 - Fix for bug #65899 */
				/* M.H. 30 Oct 2012 Fix for bug #122396 */
				if (dataSource.settings.responseDataKey !== null) {
					delete dataOptions.responseDataKey;
					if (dataOptions.schema) {
						dataOptions.schema.searchField = dataSource.settings.responseDataKey;
					}
				}
				/* M.H. 28 Apr 2014 Fix for bug #170712: When grid is bound to
				oData and paging is enabled a js error is thrown. */
				if (dataOptions.responseTotalRecCountKey === null &&
					dataSource.settings.responseTotalRecCountKey !== null) {
					delete dataOptions.responseTotalRecCountKey;
				}
				this._tds = dataSource.settings.dataSource;
				dataSource.settings.dataSource = null;
				/* M.H. 30 Oct 2012 Fix for bug #122396 */
				if ($.ig.util.isIE8 && dataOptions.callee) {
					callee = dataOptions.callee;
					dataOptions.callee = null;
				}
				/* M.H. 24 Sep 2012 Fix for bug #122201 */
				dataSource.settings = $.extend(true, {}, dataSource.settings, dataOptions);
				/* M.H. 30 Oct 2012 Fix for bug #122396 */
				if ($.ig.util.isIE8 && callee) {
					dataSource.settings.callee = callee;
				}
				dataSource.settings.dataSource = this._tds;
				this._tds = null;
				if (dataOptions.schema) {
					dataSource._initSchema();
				}
			}
			return dataSource;
		},
		_generateColumns: function () {
			var r, key, i, hasExplicitCols = this.options.columns.length > 0,
				hasHeaders = false, len, col,
				isTable = false, arr = [],
				ds = this.options.dataSource,
				cdp = this.options.childrenDataProperty,
				newDs, colType, dsHtmlTableString = false;
			/* this.options.columns = []; // A.T. 28 Feb  2011 - we shouldn't be clearing the columns !@ */
			/* we need to take into account the case where columns are already defined.
			This means we render them first, and only then proceed with the rest of the columns in the data source */
			if (ds && typeof ds._xmlToArray === "function" && typeof ds._encodePkParams === "function") {
				// M.H. 22 Oct 2014 Fix for bug #130353: When grid's data source is html table string and autoGenerateColumns is true the columns are not correct.
				if (this.options.dataSource.type() === "htmlTableString" && this.options.autoGenerateColumns) {
					dsHtmlTableString = true;
				}
				ds = ds.data(); // special case where the data source is an instance of an $.ig.DataSource
			} else if (typeof ds === "string") {
				ds = this.dataSource.data();
			}
			/* M.H. 16 May 2012 Fix for bug #99426 */
			if ($.type(ds) === "object" && this.options.responseDataKey) {
				newDs = $.ig.findPath(ds, this.options.responseDataKey);
				if ($.type(newDs) === "array") {
					ds = newDs;
				}
			}
			/* special case - having columns defined manually, and autoGenerateColumns = true at the same time */
			/* A.T. that"s basically bug (#74240) - we shouldn't be using the dataView,
			because it's already bound according to whatever is defined in options.columns
			and everything else is not bound at all ! */
			if (ds && ds.tagName && $(ds).is("table")) {
				len = $(ds).find("tbody tr").length;
				isTable = true;
			} else if (ds && ds.length) {
				len = ds.length;
			}
			if (ds && len && len === 0 && this.options.columns.length === 0) {
				throw new Error($.ig.Grid.locale.autoGenerateColumnsNoRecords);
			}
			if (ds && len && len > 0) {
				//r = this.dataSource.dataView()[0];
				if (isTable) {
					r = $(ds).find("tbody tr")[ 0 ];
				} else {
					r = ds[ 0 ];
				}
				if ($.type(r) === "array" || isTable) {
					// check if we aren't binding to a table that has headers defined already
					/*
					if (hasExplicitCols && this.options.columns[0].headerText) {
						hasHeaders = true;
						for (i = 0; i < this.options.columns.length; i++) {
							headers.push(this.options.columns[i].headerText);
						}
						this.options.columns = [];
					}
					*/
					/*jscs:disable*/
					hasHeaders = this._tb_h;
					/*jscs:enable*/
					if (isTable) {
						$(r).find("td").each(function () { arr.push($(this).text()); });
						r = arr;
					}
					for (i = 0; i < r.length; i++) {
						// detect type
						if (this.columnByKey(i + 1) === null && $.ig.getColType(r[ i ]) !== "object") {
							col = {
								/*jscs:disable*/
								headerText: hasHeaders ? this._tb_h_arr[ i ] : $.ig.Grid.locale.colPrefix + (i + 1),
								/*jscs:enable*/
								/* M.H. 25 Mar 2014 Fix for bug #159801: When the grid is bound to
								html table and the columns are autogenerated hiding does not work */
								key: String((i + 1)),
								dataType: $.ig.getColType(r[ i ]),
								hidden: false
							};
							/* M.H. 22 Oct 2014 Fix for bug #130353: When grid's data source is html
							table string and autoGenerateColumns is true the columns are not correct. */
							if (dsHtmlTableString) {
								col.key = String(i);
							}
							/* L.A. 30 November 2012 - Fixing bug #128507 */
							col.headerText = (col.headerText || "").toString().trim();
							/* M.H. 9 Aug 2012 Fix for bug #118689 */
							if (this._isMultiColumnGrid) {
								col.level0 = true;
								col.level = 0;
								this._oldCols.push(col);
							}
							this.options.columns.push(col);
							this._visibleColumnsArray = undefined;
						} else if (hasHeaders && !this.columnByKey(i + 1).headerText) {
							/*jscs:disable*/
							this.columnByKey(i + 1).headerText = this._tb_h_arr[ i ];
							/*jscs:enable*/
						}
					}
				} else {
					for (key in r) {
						// also detect type
						if (r.hasOwnProperty(key) && this.columnByKey(key) === null) {
							/*L.A. 04 October 2012 - Fixing bug #123318
							If a column has a null value for the first call and autoGenerateColumns is true the column isn't generated. */
							colType = $.ig.getColType(r[ key ]);
							if (((cdp && cdp !== key) || !cdp) &&
								(colType !== "object" || r[ key ] === null) && key !== "ig_pk") {
								col = {
									headerText: key,
									key: key,
									dataType: $.ig.getColType(r[ key ]),
									hidden: false
								};
								this.options.columns.push(col);
								/* M.H. 9 Aug 2012 Fix for bug #118689 */
								if (this._isMultiColumnGrid) {
									col.level0 = true;
									col.level = 0;
									this._oldCols.push(col);
								}
								this._visibleColumnsArray = undefined;
							}
						}
					}
				}
			}
			this._trigger("_columnsgenerated", null, { owner: this, key: this.options.key });
			/* we need to set the data source schema */
			if ((this.dataSource.schema() === null ||
				this.dataSource.schema().fields().length === 0) &&
				!hasExplicitCols) {
				this.dataSource.settings.schema = this._generateDataSourceSchema();
				this.dataSource._initSchema();
			}
			if (this.options.width === null) {
				this._setContainerWidth(this.container());
			}
			this._trigger("columnsgenerated", null, { owner: this, key: this.options.key });
		},
		_renderGrid: function () {
			var gridElement = this.element[ 0 ],
				containerId,
				containerDiv,
				tbody = this.element.children("tbody"); //gridElement.find('tbody')
			/* ar = this.options.accessibilityRendering; */

			this._cancelRendering = false;
			if (!this._initialized) {
				// we should have the data now in the data view
				// determine automatically if we want virtualization enabled or not
				// IMPORTANT: height must also be always set !
				//if ($.type(this.options.virtualization) === "number" && this.dataSource.dataView().length > this.options.virtualization && this.options.height !== null) {
				//	this.options.virtualization = true;
				//}
				//if (ar) {
				this.element.attr("role", "grid");
				if (this.options.virtualization === true ||
					this.options.rowVirtualization === true ||
					this.options.columnVirtualization === true) {
					if (this.options.height === undefined || this.options.height === null) {
						throw new Error($.ig.Grid.locale.virtualizationRequiresHeight);
					}
					if (this._isColumnVirtualizationEnabled() &&
						(this.options.width.indexOf && this.options.width.indexOf("%") > 0)) {
						//M.K. 1/13/2014 187174: Errors should be thrown when the grid is initialized with unsupported configurations
						//Column virtualization will not work when grid width is defined in percentage units
						throw new Error(
							$.ig.Grid.locale.columnVirtualizationNotSupportedWithPercentageWidth
						);
					}
					this._createVirtualGrid();
				} else if (this.options.height !== null || this.options.width !== null) {
					this._createScrollingGrid();
				} else {
					// just wrap with a div, if it doesn't exist
					//if (!this._isWrapped) {
					containerId = gridElement.id + "_container";
					containerDiv = "<div id=\"" + containerId +
						"\" class=\"" + this.css.gridClasses + " " + this.css.baseClass +
						"\" style=\"position: relative\"> </div>";
					this.element.wrap(containerDiv);
					/*} else {
						containerId = this.element.parent().addClass(this.css.gridClasses).addClass(this.css.baseClass).attr("id");
					} */
					this.element.addClass(this._isMultiRowGrid() ?
						this.css.mrlGridTableClass : this.css.gridTableClass);
					/* if (ar) { */
					this.element.attr("aria-describedby", containerId);
					/* } */
					this._setContainerWidth(this.container());

					this.container().attr("tabIndex", this.options.tabIndex);
					if (this.options.height !== null) {
						this.container().css("overflow-y", "hidden");
						/* this.scrollContainer().css("height", this.options.height); */
					}
				}
				/* touch support code */
				this._touch();
				/* render colgroup for column widths */
				if (this.options.columns.length > 0 &&
					(this.options.virtualization !== true &&
					this.options.rowVirtualization !== true &&
					this.options.columnVirtualization !== true) &&
					this.options.autogenerateColumns === false &&
					this.options.columns.length > 0) {
					this._renderColgroup(this.element[ 0 ], false, false, this.options.autofitLastColumn);
				}
				/* cellpadding, cellspacing, etc. */
				$(gridElement).attr("cellpadding", "0");
				$(gridElement).attr("cellspacing", "0");
				$(gridElement).attr("border", "0");
				$(gridElement).css("table-layout", "fixed");
				$(gridElement).addClass(this._isMultiRowGrid() ?
					this.css.mrlGridTableClass : this.css.gridTableClass);
				if ((this.options.autoGenerateColumns === false && !this._autoDetectColTypes()) &&
					this.options.columns.length > 0 &&
						this._headerRenderCancel !== true) {
					this._renderHeader();
				}
				/* render header caption */
				this._renderCaption();
				if (this.options.autoAdjustHeight) {
					this._initializeHeights();
				}
			}
			if (tbody.length === 0) {
				tbody = $("<tbody role=\"rowgroup\"></tbody>")
					.appendTo(gridElement)
					.addClass(this.css.baseContentClass)
					.addClass(this.css.gridTableBodyClass)
					.addClass(this.css.recordClass);
			}
			if (this.dataSource.type() !== "htmlTableDom" &&
				this.dataSource.type() !== "htmlTableId") {
				tbody.attr("role", "rowgroup").empty();
				/* M.H. 4 Jan 2016 Fix for bug 211113: Script error is thrown when a grid is rebound to
				an empty datasource if there are fixed columns and virtualizationMode is set continuous. */
				if (this.hasFixedColumns()) {
					this.fixedBodyContainer()
						.children("table")
						.children("tbody")
						.attr("role", "rowgroup")
						.empty();
				}
			}
		},
		_autoDetectColTypes: function () {
			// go through columns collection and check whether data type is defined
			var i, cols = this.options.columns;
			for (i = 0; i < cols.length; i++) {
				if (!cols[ i ].dataType || cols[ i ].mapper) {
					return true;
				}
			}
			return false;
		},
		_setContainerWidth: function (element, rendered) {
			var cols = this._visibleColumns(), i, w, width = 0, inPerc;
			/* calculate based on column widths */
			/* if no col width is set, use the defaultColumnWidth */
			/* if (cols.length > 0 && !this.options.autoGenerateColumns) { */
			if (cols.length > 0) {
				// M.H. 1 Sep 2015 Fix for bug 205476: Grid width becomes very small after column is resized and hidden
				inPerc = false;
				/*  if all columns are in percentage OR are not set then
				do not call _calculateContainerWidth - exit the function */
				for (i = 0; i < cols.length; i++) {
					w = cols[ i ].width;
					if (w !== 0 && w !== "0") {
						w = w || this.options.defaultColumnWidth;
					}
					if (_aNull(w) ||
						(w && w.indexOf && w.indexOf("%") > 0)) {
						inPerc = true;
					} else {
						inPerc = false;
						break;
					}
				}
				/* if ALL columns are in percentage or haven't width set - do not call
				_calculateContainerWidth(the width will not be properly calculated). */
				if (inPerc) {
					return;
				}
				/* it is added width of vertical scrollbar to the total width of container.
				But it is possible when vertical scrollbar is not rendered - e.g. height is
				null OR height of rendered TRs is less than height of visible area. */
				/* In function _adjustLastColumnWidth it is checked whether vertical scrollbar
				is rendered - if NOT and width of vertical scrollbar  is added to the width of the
				container - then container width should be recalculated - fix for bug 209777. */
				/* M.H. 18 Nov 2015 Fix for bug 209777: When the columns have width
				and grid's width option is not set the grid does not have a proper width. */
				this._scrollWidthAddedToContainerWidth = (!!this.options.height); // if height is null then vertical scrollbar should not be rendered
				width = this._calculateContainerWidth(this._scrollWidthAddedToContainerWidth);
				if (width > 0) {
					if (rendered) {
						// get outer widths for column headers
						width = 0;
						this.container().find(".ui-iggrid-header").each(function () {
							width += $(this).outerWidth();
						});
						element.width(width);
					} else {
						width += this._calculateSpecialColumnsWidth();
						element.css("width", width);
					}
				}
			} else if (this.options.width !== null) {
				element.css("width", this.options.width);
			}
		},
		_calculateContainerWidth: function (addScrollWidth) {
			var width = 0, cols = this.options.columns, i;
			for (i = 0; i < cols.length; i++) {
				//check if the columns is hidden
				//or will be hidden if we are still in the initialization phase
				if (cols[ i ].hidden !== true &&
					cols[ i ].fixed !== true &&
					(this._initialHiddenColumns === undefined ||
					$.inArray(cols[ i ], this._initialHiddenColumns) === -1)) {
					width += cols[ i ].width ? parseInt(cols[ i ].width, 10) :
						this.options.defaultColumnWidth === null ?
						0 : parseInt(this.options.defaultColumnWidth, 10);
				}
			}
			/* add the scrollbar width if any */
			/* L.A. 30 March 2012 Fixed bug #99024 When in grid
			fixedHeaders is false, it is not possible to resize last column */
			if (this.options.height !== null && width > 0 && addScrollWidth === true) {
				//do not add the scrollbar width in case we have virtualization and no width
				width += this._scrollbarWidth();
			}
			return width;
		},
		/* creates a scrolling, non-virtual grid */
		_createScrollingGrid: function () {
			var self = this, id = this.id() + "_scroll", dataContainer,
				scrollDiv = "<div id=\"" + id + "\"></div>";
			this.element.wrap(scrollDiv);
			/* dataContainer = $('#' + id); */
			dataContainer = this.element.parent();
			/* if (this.options.accessibilityRendering) { */
			this.element.attr("aria-describedby", id);
			/* } */
			dataContainer.addClass(this.css.gridScrollDivClass)
				.wrap("<div id=\"" + this.id() + "_container\"></div>");
			/* L.A. 08 October 2012 - Fixing bug #121790
			The horizontall scollbar overlaps the last visible row when using paging
			Scrollbar height is the same as _scrollbarWidth */
			if ($.ig.util.isIE7) {
				dataContainer.css("padding-bottom", this._scrollbarWidth());
			}
			this.container().attr("tabIndex", this.options.tabIndex)
				.addClass(this.css.baseClass)
				.addClass(this.css.gridClasses);
			if (this.options.width !== null) {
				this.container().css("width", this.options.width);
				/*  A.T. 14 Feb 2011 - Fix for bug #66086 */
				if (this.options.width.indexOf && this.options.width.indexOf("%") !== -1) {
					//if ((this.options.width.indexOf("%") !== -1 && parseInt(this.options.width, 10) === 100) || this.options.width.indexOf("%") === -1) {
					//this.element.css("width", this.options.width);
					//} else if (this.options.width.indexOf("%") !== -1) {
					//if (this.options.width.indexOf("%") !== -1) {
					this.element.css("width", "100%");
				}// else {
				/* this.element.css("width", this.options.width);
				} */
				if (this.options.height !== null) {
					this._addHorizontalScrollBar(dataContainer);
					this.scrollContainer().css("overflow-x", "hidden");
				}
			} else {
				this._setContainerWidth(this.container());
				/* set overflow-x: hidden on the scrolling container */
				this.scrollContainer().css("overflow-x", "hidden");
			}
			this.container().css("position", "relative");
			if (this.options.height !== null) {
				this.scrollContainer().css("overflow-y", "auto");
				if (this.options.autoAdjustHeight) {
					this.container().css("height", this.options.height);
				} else {
					this.scrollContainer().css("height", this.options.height);
				}
			}
			/* M.K. Preserve scroll position after dataBind */
			if (this._persistVirtualScrollTop) {
				this.scrollContainer().bind({
					scroll: function () {
						self._prevFirstVisibleTROffset = self.scrollContainer().scrollTop();
					}
				});
			}

			// M.P.: 194417 - adding support for horizontal scrolling using the mouse wheel/trackpad
			// Note that we're targeting the standards based wheel event, which means that this won't work for older browsers
			this.scrollContainer().bind({
				wheel: function (event) {
					self._hscrollbarcontent()
						.scrollLeft(self._hscrollbarcontent().scrollLeft() + event.originalEvent.deltaX);
				}
			});
			/*
			// touch scroll support
			if (this.options.height !== null && this.options.width !== null) {
				this.scrollContainer().attr("data-scroll", "true");
			} else if (this.options.height !== null) {
				this.scrollContainer().attr("data-scroll", "y");
			} else if (this.options.width !== null) {
				this.scrollContainer().attr("data-scroll", "x");
			}
			*/
		},
		_touch: function () {
			var id = this.id(), div = this.scrollContainer();
			if (div.length !== 1) {
				div = this._vdisplaycontainer();
			}
			if (div.length) {
				div.attr("data-scroll", "true").attr("data-oneDirection", "true");
				if (this._hscrollbarcontent()[ 0 ]) {
					div.attr("data-xScroller", "#" + id + "_hscroller");
				} else if (this._vhorizontalcontainer()[ 0 ]) {
					div.attr("data-xScroller", "#" + id + "_horizontalScrollContainer");
				}
				if (this._scrollContainer()[ 0 ]) {
					div.attr("data-yScroller", "#" + id + "_scrollContainer");
				}
				/*  M.H. 20 March 2012 Fix for bug #104415 */
				if ($.ig.util.isTouch && this.element.igScroll !== undefined) {
					// M.H. 1 Sep 2014 Fix for bug #177790: Column misalignment on touch devices when there is a igGrid.height set
					this._scrollbarWidthResolved = 0;
					div.css("overflow-y", "hidden");
				}
			}
		},
		/* virtual grid implies scrolling grid */
		_createVirtualGrid: function () {
			/*
			*  // this is the general structure for virtualization when both column and row virtualization are enabled
			*	<table border="0" cellspacing="0" cellpadding="0">
			*		<tr>
			*			<td><div id="#gridID_headers"></div></td>
			*			<td></td>
			*		</tr>
			*			<tr>
			*				<td><div id="#gridID_displayContainer"></td>
			*				<td><div id="#gridID_scrollContainer"></div></td>
			*			</tr>
			*			<tr>
			*			<td><div id="#gridID_horizontalScrollContainer"></div></td>
			*			<td></td>
			*			</tr>
			*	</table>
			*/
			var id = this.id(), $vCont, evnts, $vDsplCntnr,
				/* touchstart,
				touchend, */
				grid,
				newW,
				percWidthStr = $.ig.util.isWebKit ? "width=100%" : "",
				totalWidth,
				scrollContainerInner,
				scrollbarWidth,
				w = 0,
				virtualGridMarkup = "<div id=\"" + id + "_container\" style=\"margin:0px; border:0px; " +
					"padding:0px;\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" " +
					"class=\"ui-iggrid-layout-helper\" style=\"border-spacing:0px\" id=\"" + id +
					"_virtualContainer\" ><tbody role=\"rowgroup\"><tr><td colspan=\"2\" " +
					"style=\"border-width:0px\"><div id=\"" + id + "_headers_v\" " +
					"style=\"overflow:hidden;\"></div></td></tr><tr><td style=\"border-width:0px;\"><div id=\"" +
					id + "_displayContainer\"></td>$verticalMarkup$</tr>$horizontalMarkup$</tbody></table></div>",
				verticalMarkup,
				horizontalMarkup = "<tr><td colspan=\"2\" style=\"border-width: 0px\"><div id=\"" +
				id + "_horizontalScrollContainer\"></div></td></tr>";
			scrollbarWidth = this._scrollbarWidth();
			if ($.ig.util.isIE) {
				scrollbarWidth += 1;
			}
			if (parseInt(this.options.height, 10) > 0) {
				verticalMarkup = "<td style=\"border-width: 0px;\"><div id=\"" + id +
					"_scrollContainer\" style=\"overflow:scroll; overflow-x:hidden; width: " +
					scrollbarWidth + "px; height:" + this.options.height + ";\"></div></td>";
			} else {
				verticalMarkup = "<td style=\"border-width: 0px;\"><div id=\"" + id +
					"_scrollContainer\" style=\"overflow:scroll; overflow-x:hidden; width: " +
					scrollbarWidth + "px;\"></div></td>";
			}
			if (this.options.virtualization === true) {
				virtualGridMarkup = virtualGridMarkup.replace("$verticalMarkup$", verticalMarkup)
					.replace("$horizontalMarkup$", horizontalMarkup);
			} else if (this.options.rowVirtualization === true) {
				virtualGridMarkup = virtualGridMarkup.replace("$verticalMarkup$", verticalMarkup)
					.replace("$horizontalMarkup$", "");
			} else if (this.options.columnVirtualization === true) {
				virtualGridMarkup = virtualGridMarkup.replace("$horizontalMarkup$", horizontalMarkup)
					.replace("$verticalMarkup$", "");
			}
			/* if column virtualization is enabled we really need to make sure
			that we set the width of the data table to 100 % otherwise the column
			widths will not be correct and will try to be accomodated in the
			specified fixed width of the grid. */
			if (this.options.virtualization === true ||
				this.options.columnVirtualization === true) {
				this.element.css("width", "100%");
			}
			/* now inject our existing grid in the place of the "displayContainer" */
			this.element.wrap(virtualGridMarkup);
			/* apply the base classes */
			/* M.H. 23 Apr 2014 Fix for bug #170317: Can't set focus to
			grid container when row virtualization is enabled */
			this.container().attr("tabIndex", this.options.tabIndex)
				.addClass(this.css.baseClass)
				.addClass(this.css.gridClasses);
			if (this.options.width !== null) {
				/* M.H. 21 Mar 2014 Fix for bug #159951: When virtualization is
				enabled in a grid with % width (not 100%), the layout is broken */
				/* if (this.options.width.indexOf && this.options.width.indexOf("%") === -1) { */
				this.container().width(this.options.width);
				/* } else {
				this.container().width("100%");
				} */
			} else {
				this._setContainerWidth(this.container());
			}
			/* M.H. 21 Nov 2014 Fix for bug #185538: Row height gets wider to fill the
			empty space if summarized row is displayed and virtualizationMode is set “continuous”. */
			this._vdisplaycontainer()
				.addClass(this.css.gridScrollDivClass)
				.addClass(this.css.gridVirtualScrollDivClass)
				.append(this.element[ 0 ]);
			/* I.I. bug fix for 105701, 105703. When virtualization is continuous
			and grid is hierarchical, this line causes header misplacements. */
			/* I.I. bug fix for 110664 */
			/* M.H. 21 May 2012 Fix for bug #112167 - renderColgroup will call
			visibleColumns which sets incorrect number of visible columns at init */
			/*
			if (this.options.virtualization === true && this.options.virtualizationMode === "fixed") {
				this._renderColgroup(this.element[0], false, false, this.options.autofitLastColumn);
			}
			*/
			grid = this;
			totalWidth = this._calculateContainerWidth(false);
			if (this.options.width !== null) {
				w = parseInt(this.options.width, 10);
			} else {
				w = totalWidth;
			}
			if (this.options.height !== null && this.options.width !== null) {
				w -= this._scrollbarWidth();
			}
			/* A.Y. bug 98976. Make the width undefined if 0 or less so that
			it is ignored by jquery as the width of the container. */
			if (w <= 0) {
				w = undefined;
			}
			/* M.H. 11 April 2012 Fix for bug #108438 */
			if (w > 0 && this.options.expandColWidth && !this.options.width) {
				w += this.options.expandColWidth;
			}
			/* set virtual container's width correctly */
			$("<colgroup><col " + (this._gridHasWidthInPercent() ?
				"" : (w <= 0 ? percWidthStr : ("width=\"" + w + "\""))) + "></col><col width=\"" +
				this._scrollbarWidth() + "\"></col></colgroup>").prependTo(this._virtualcontainer());
			newW = this.options.width;
			/* M.H. 4 Apr 2014 Fix for bug #159951: When virtualization is
			enabled in a grid with % width (not 100%), the layout is broken */
			if (newW && newW.indexOf && newW.indexOf("%") !== -1) {
				newW = "100%";
			}
			this._virtualcontainer().css("width", newW).css("max-width", newW);
			/* now create the inner scrolling containers, that will be placed
			inside of the scrollContainer/horizonalScrollContainer, аnd will
			cause the virtual scrollbars to appear and grow according to the
			total records in the data source */
			scrollContainerInner = "<div style=\"width:1px; overflow:hidden; height:" +
				(this._totalRowCount * parseInt(this.options.avgRowHeight, 10)) + "px;\"></div>";
			this._scrollContainer().append(scrollContainerInner);
			/* M.H. 31 May 2016 Fix for bug 219580: Rows height fills the whole grid height when fixed virtualization is enabled and autoAdjustHeight is set to true after sorting */
			if ($.ig.util.isFF && this.options.virtualizationMode === "fixed") {
				this._scrollContainer().height(0);
			}
			/* M.H. 9 June 2014 Fix for bug #173336: Horizontal scrollbar
			does not appear when row virtualization is enabled */
			/* M.H. 18 Aug 2014 Fix for bug #177656: When virtualization is enabled
			resizing a column of a grid with 100% width makes the grid expand */
			if ((this.options.virtualization === true || this.options.rowVirtualization === true) &&
				this.options.width && this.options.width.indexOf && this.options.width.indexOf("%") > 0) {//totalWidth > $("#" + id + "_container").width()
				this._addHorizontalScrollBar(this._virtualcontainer());
				this._virtualcontainer().css("table-layout", "fixed");
			} else if ((this.options.virtualization === true ||
				this.options.columnVirtualization === true) &&
				this.options.width && (totalWidth > parseInt(this.options.width, 10))) {
				//we always need to have the horizontal scroll containers if we have width
				//as there may be hidden columns that when visible will show a scrollbar
				//I.I. bug fix for 110369
				// do the same for the horizontal scrolling
				// M.H. 5 Jul 2013 Fix for bug #145572: When Resizing with continuous rowVirtualization no horizontal scrollbar appears when needed
				// we need to render horizontal scroll container when virtualization mode is continuous
				// if we use another solution to show/hide horizontal scroll container then height is not properly calculated
				this._renderHorizontalScrollContainer(totalWidth);
			}
			if (parseInt(this.options.height, 10) > 0) {
				this._vdisplaycontainer().css("height", this.options.height).css("vertical-align", "top");
			}
			/* I.I. bug fix for 104085, tabindex=-1 added */
			/* L.A. fixed bug #105997 Can't navigate selection with arrow keys when fixed virtualization is enabled
			Anchor should have some content in order to have get a focus. The content is visible but outside of the visible area. */
			/* L.A. fixed bug #112519 When continuous virtualization and selection with mode = row are enabled scrolling down the grid and */
			/* selecting a row changes records values with wrong ones. */
			/* L.A. fixed bug #113287 fixed appending to the _virtualContainer */
			this._vdisplaycontainer().css("position", "relative").css("width", w).css("maxWidth", w);
			/* L.A. fixed bug #101375 moving the anchor to _headers_v, because it's visible in IE8, IE9 */
			if (this.options.virtualization === true) {
				this.container().find("#" + id + "_headers_v")
					.append("<a href=\"#\" id=\"" + id + "_displayContainer_a\" tabindex=\"-1\" " +
					"style=\"position:absolute;top:-100px;left:-100px\">&nbsp;</a>");
			}
			if (this.options.width &&
				this.options.virtualization === false &&
				this.options.columnVirtualization === false) {
				this._vdisplaycontainer().css({ "overflow-y": "hidden", "overflow-x": "auto" });
			} else {
				this._vdisplaycontainer().css("overflow", "hidden");
			}
			/* make sure mouse wheel scrolling also works for the table with data, not only the virtual scrollbar
			it's a smart and little-known technique i am going to use here -:) */
			/* I.I. bug fix for 109777, mouseover -> mouseenter, mouseout -> mouseleave */
			this._vdisplaycontainer().parent().bind({
				mouseenter: function () {
					grid._isMouseOverVirtualTable = true;
				},
				mouseleave: function () {
					grid._isMouseOverVirtualTable = false;
				}
			});
			/*
			touchstart = function (event) {
				this._oldPageY = event.touches[0].pageY;
				event.stopPropagation();
				event.preventDefault();
			};
			touchend = function (event) {
				// compare pageY and pageX with the old ones
				if (event.changedTouches[0].pageY - this._oldPageY > 0) {
					grid._onVirtualVerticalScroll(event, Math.abs(event.changedTouches[0].pageY - this._oldPageY), 'up');
				} else if (event.changedTouches[0].pageY - this._oldPageY < 0) {
					grid._onVirtualVerticalScroll(event, Math.abs(event.changedTouches[0].pageY - this._oldPageY), 'down');
				}
			};
			$('#' + id + '_displayContainer').parent()[0].addEventListener("touchstart", $.proxy(touchstart, this), false);
			$('#' + id + '_displayContainer').parent()[0].addEventListener("touchend", $.proxy(touchend, this), false);
			*/
			/* Refactor to keep in one place */
			/* M.H. 6 Nov 2013 Fix for bug #156713: Scrolling with the mouse wheel resultsin looping
			through a subset of the rows when using continuous virtualization in Chrome, Firefox, and Safari */
			this._documentEvents = {
				DOMMouseScroll: function (event) { // Firefox
					var dir = "down", delta, step, deltaX;
					step = grid.options.virtualizationMouseWheelStep === null ?
						parseInt(grid.options.avgRowHeight, 10) :
						grid.options.virtualizationMouseWheelStep;
					/* I.I. bug fix for 104505. The issue is reproducible with jquery 1.7.1 only */
					delta = -event.originalEvent.detail / 3; // determine if we are scrolling up or down
					if (delta > 0) { // scroll up
						dir = "up";
					}  // else default => scroll down
					/* determine if mouse over  is true */
					if (grid._isMouseOverVirtualTable) {
						if (event.originalEvent.axis === 2) {
							grid._onVirtualVerticalScroll(event, step, dir); // define this # of pixels automatically
						}
						deltaX = event.originalEvent.offsetX;
						if (event.originalEvent.axis === 1) {
							grid._vdisplaycontainer().scrollLeft(grid._vdisplaycontainer().scrollLeft() - delta);
						}
						event.preventDefault();
					}
				},
				mousewheel: function (event) { // IE, Chrome, Safari, Opera
					var dir = "down", delta, step;
					step = grid.options.virtualizationMouseWheelStep === null ?
						parseInt(grid.options.avgRowHeight, 10) :
						grid.options.virtualizationMouseWheelStep;
					/* I.I. bug fix for 104505. The issue is reproducible with jquery 1.7.1 only */
					delta = (event.originalEvent.wheelDeltaY === undefined ?
						event.originalEvent.wheelDelta :
						event.originalEvent.wheelDeltaY) / 120; // determine if we are scrolling up or down
					if (delta > 0) {
						dir = "up";
					}
					if (grid._isMouseOverVirtualTable) {
						if (delta !== 0) {
							grid._onVirtualVerticalScroll(event, step, dir); // define this # of pixels automatically
						}
						grid._vdisplaycontainer()
							.scrollLeft(grid._vdisplaycontainer().scrollLeft() - event.originalEvent.wheelDeltaX);
						event.preventDefault();
					}
				},
				wheel: function (event) { // Standards based wheel event
					var dir = "down", step, delta;
					step = grid.options.virtualizationMouseWheelStep === null ?
						parseInt(grid.options.avgRowHeight, 10) :
						grid.options.virtualizationMouseWheelStep;
					delta = -event.originalEvent.deltaY;
					if (delta > 0) {
						dir = "up";
					}
					if (grid._isMouseOverVirtualTable) {
						if (delta !== 0) {
							grid._onVirtualVerticalScroll(event, step, dir); // define this # of pixels automatically
						}
						grid._vdisplaycontainer()
							.scrollLeft(grid._vdisplaycontainer().scrollLeft() + event.originalEvent.deltaX);
						event.preventDefault();
					}
				}
			};
			evnts = {};
			$vDsplCntnr = this._vdisplaycontainer();
			if (this.options.virtualizationMode === "fixed") {
				evnts[ "keydown.virtualizationEvents" ] = function (e) {
					if (e.keyCode === $.ui.keyCode.TAB) {
						grid._syncScrollOnTabFixedVirt(e, e.shiftKey ? "up" : "down", $vDsplCntnr);
					}
				};
			} else {
				evnts[ "keydown.virtualizationEvents" ] = function (e) {
					if (e.keyCode === $.ui.keyCode.TAB) {
						grid._syncScrollOnTab(e, e.shiftKey ? "up" : "down", $vDsplCntnr);
					}
				};
			}
			$vDsplCntnr.bind(evnts);
			this._documentEvents[ "keydown." + this.id() ] = function (event) {
				var keyCode = event.keyCode, $sc,
					dir = null, step;
				if (grid._isMouseOverVirtualTable) {
					if (keyCode === $.ui.keyCode.DOWN) {
						dir = 1;

					} else if (keyCode === $.ui.keyCode.UP) {
						dir = -1;
					}
					if (dir && grid._isMouseOverVirtualTable) {
						step = grid.options.virtualizationMouseWheelStep === null ?
							parseInt(grid.options.avgRowHeight, 10) :
							grid.options.virtualizationMouseWheelStep;
						$sc = grid._scrollContainer();
						$sc.scrollTop($sc.scrollTop() + step * dir);
					}
				}
			};
			$(document).bind(this._documentEvents);
			/* bind scroll event handlers */
			if (this.options.virtualization === true || this.options.rowVirtualization === true) {
				this._scrollContainer().bind({
					scroll: function (event) {
						grid._onVirtualVerticalScroll(event);
						/* bugs #70681  and bug #72116 */
						grid._virtualScrollMouseDown = false;
					},
					mousedown: function () {
						// this is necessary because of one special case. When we scroll just once, we want to move by 1 row always (in chrome and FF)
						// but when we scroll continuously, we don"t want the scrollbar to jump (Refer to bugs #70681  and bug #72116
						grid._virtualScrollMouseDown = true;
					}
				});
			}
			if (this.options.virtualization === true || this.options.columnVirtualization === true) {
				$vCont = this._vhorizontalcontainer();
				$vCont
					.data("containerName", "vScrollbar")
					.bind({
						scroll: function (event) {
							grid._onVirtualHorizontalScroll(event);
						}
					});
				if (this.options.virtualizationMode === "continuous") {
					this._registerScrllCntnrToSync($vCont);
				}
			}
			/* S.S. May 11, 2012, #103088 We need to use a normal horizontal scroll container
			when we use only vertical virtualization and the set grid's width is too narrow */
			if (this._vhorizontalcontainer().length === 0 && this.options.width !== null) {
				/* add the scrollbar */
				/* M.H. 4 Apr 2014 Fix for bug #159951: When virtualization
				is enabled in a grid with % width (not 100%), the layout is broken */
				if (!this.options.width.indexOf || this.options.width.indexOf("%") === -1) {
					this._addHorizontalScrollBar(this._virtualcontainer());
				}
				/* changes the overlow-x attribute of the main container */
				this._vdisplaycontainer().css("overflow-x", "hidden");
			}
			this.element.height(this._scrollContainer().height());
			/* A.T. 16 Oct. Fix for bug #123235 */
			if (this.options.width &&
				this.options.width.indexOf &&
				this.options.width.indexOf("%") !== -1) {
				this.container()
					.find("#" + id + "_virtualContainer > colgroup > col:first")
					.css("width", "100%");
				this._vhorizontalcontainer().css("width", "100%");
				this._vdisplaycontainer().css("width", "100%").css("max-width", "100%");
			}
		},
		_syncScrollOnTabFixedVirt: function (e, dir, $vDsplCntnr) {
			// sync scroll top  (when virtualizatoin is fixed) AND navigating through cells using TAB/Shift+TAB
			// Fix for bug 205887 - this issue could be reproduced when virtualization mode is fixed as well
			$vDsplCntnr = $vDsplCntnr || this._vdisplaycontainer();
			var $ae = $(document.activeElement), $next,
				isDown = dir === "down", scrlTop, $scrlCntnr, h,
				selFirstLastChild = isDown ? ":last-child" : ":first-child";
			$next = isDown ? $ae.closest("tr").next() : $ae.closest("tr").prev();
			/* when TAB is pressed then it should be on the last cell of the
			last(rendered) TR to scroll and get next chunk of table rows
			if SHIFT + TAB is pressed THEN if should be on the first cell of
			the first(rendered) TR to scroll UP and get previous chunk of table rows */
			if (!$ae.is("td, th") ||
				!$ae.is(selFirstLastChild) ||
				$next.length) {
				return;
			}
			$scrlCntnr = this._scrollContainer();
			scrlTop = $scrlCntnr.scrollTop();
			h = parseInt(this.options.avgRowHeight, 10);
			scrlTop += isDown ? h : -h;
			$scrlCntnr.scrollTop(scrlTop);
			if (scrlTop <= 0 ||
				scrlTop + $vDsplCntnr.outerHeight() >= $scrlCntnr.children("div").outerHeight()) {
				return;
			}
			e.preventDefault();
		},
		_syncScrollOnTab: function (e, dir, $vDsplCntnr) {
			// sync scroll top  (when virtualizatoin is continuous) AND navigating through cells using TAB/Shift+TAB - Fix for bug 205887
			// called from keydown - event is thrown before focusing to the next element(take into account when detecting (next)focused element whether is inside visible area)!
			$vDsplCntnr = $vDsplCntnr || this._vdisplaycontainer();
			var $ae = $(document.activeElement), $next, $currTr,
				$scCntnr, scrTop, id,
				isDown = dir === "down",
				selFirstLastChild = isDown ? ":last-child" : ":first-child";
			if (!$ae.is("td, th") || !$ae.is(selFirstLastChild)) {
				return;
			}

			$currTr = $ae.closest("tr");
			$next = isDown ? $currTr.next() : $currTr.prev();
			this._focusDataRowIndex = null;
			if (!$next.length) {
				$scCntnr = this._scrollContainer();
				scrTop = $scCntnr.scrollTop();
				if (isDown) {
					if ($scCntnr[ 0 ].scrollHeight - scrTop === $scCntnr.outerHeight()) {
						this._focusDataRowIndex = 0;
					} else {
						$scCntnr.scrollTop(scrTop + 2);
						id = parseInt($currTr.attr("data-row-idx"), 10);
						this._focusDataRowIndex = !isNaN(id) && id - 1 > 0 ? id - 1 : null;
					}

					return;
				} else if (!isDown) {
					if (!scrTop) {
						this._focusDataRowIndex = this._getTotalRowsCount() - 1;
						return;
					} else {
						$scCntnr.scrollTop(scrTop - 2);
						id = parseInt($currTr.attr("data-row-idx"), 10);
						this._focusDataRowIndex = !isNaN(id) && id - 1 > 0 ? id - 1 : null;
					}
				}
				e.preventDefault();
				return;
			}
			this._focusDataRowIndex = parseInt($next.attr("data-row-idx"), 10);
			this._onVirtualVerticalScroll(e, $next.outerHeight(), dir);
		},
		/* M.H. 5 Jul 2013 Fix for bug #145572: When Resizing with continuous
		rowVirtualization no horizontal scrollbar appears when needed */
		/* render horizontal scroll container when virtualization mode is continuous */
		_renderHorizontalScrollContainer: function (totalWidth) {
			var horizontalScrollContainerInner, w = this.options.width,
				$vhc = this._vhorizontalcontainer();
			/* M.H. 4 Apr 2014 Fix for bug #159951: When virtualization is
			enabled in a grid with % width (not 100%), the layout is broken */
			if (w && w.indexOf && w.indexOf("%") !== -1 &&
				(this.options.virtualization === true || this.options.rowVirtualization === true)) {
				return;
			}
			/* do the same for the horizontal scrolling */
			$vhc.css("height", this._scrollbarWidth() + "px").css("overflow", "scroll");
			/* if (this.options.virtualization === true || this.options.rowVirtualization === true) {
			$('#' + id + '_horizontalScrollContainer').css('width', parseInt(this.options.width, 10) - this._scrollbarWidth());
			} else { */
			w = this.hasFixedColumns() ? parseFloat(w) - this.fixedBodyContainer().outerWidth() : w;
			if ($.ig.util.isIE) {
				/* M.H. 3 Sep 2012 Fix for bug #118189: When virtualization is enabled,
				clicking on the horizontal scrollbar does not scroll the grid in IE9 */
				/* M.H. 12 June 2015 Fix for bug 201174: With column virtualization
				enabled hiding and then showing a column will result in wrong header text */
				/* in case of column virtualization when call initializeHeights grid
				has 0 height because height of vhorizontalContainer isn"t properly calculated */
				$vhc.css("width", parseInt(w, 10) + 1)
					.css("height", ($vhc.outerHeight() + 1) + "px");
			} else {
				/* M.H. 10 May 2016 Fix for bug 217001: Resizing does not take into account the
				vertical scrollbar and some of cell text is not fully visible after resizing */
				if ($.ig.util.isFF &&
					($.type(w) === "number" || (w && w.indexOf && w.indexOf("px") > 0))) {
					w = parseFloat(w) - this._scrollbarWidth();
				}
				$vhc.css("width", w);
			}
			horizontalScrollContainerInner = "<div style=\"width:" + totalWidth + "px;height:1px;\"></div>";
			$vhc.append(horizontalScrollContainerInner);
		},
		_suppressVirtVertScroll: function (suppress) {
			this._virtVertScrollSuppressed = suppress;
		},
		_isVirtVertScrollSuppressed: function () {
			if (this._virtVertScrollSuppressed) {
				//this._virtVertScrollSuppressed = false;
				return true;
			}
			return false;
		},
		_correctVirtVertScrollTop: function (oldScrollTop, oldScrollContHeight) {
			// M.H. 8 Apr 2016 Fix for bug 217501: If the height of the rows of the next chunk is bigger than the previous, you scroll the grid with virtualScrollTo method and collapse a row scrolling the grid throws an error.
			// changing avgRowHeight - causes changing height of virtual container - it should be reset scrollTop position of virtual container in this case
			// it should not be re-rendered virtual records(scroll event will be fired) in _onVirtualVerticalScroll
			this._suppressVirtVertScroll();
			this._setScrollContainerScrollTop(
				(oldScrollTop / oldScrollContHeight) * this._getScrollContainerHeight()
			);
			this._suppressVirtVertScroll(false);
		},
		/* if offset is defined, this means there is mouse-wheel scroll which
		we are manually handling. the offset is the amount of px to move - up or down */
		_onVirtualVerticalScroll: function (event, offset, dir) {
			this._isHorizontal = false;
			if (this._isVirtVertScrollSuppressed()) {
				// when _correctVirtVertScrollTop is called then it is possible to be scrolled grid SO it should not be re-rendered virtual records in this case
				return;
			}
			/* originalEvent = event.originalEvent, */
			var newSri, scrollContainer = this._scrollContainer(), scrollTopDiff,
				isIE = $.ig.util.isIE,
				scrollerHeight,
				avgRowHeight = parseInt(this.options.avgRowHeight, 10),
				current = scrollContainer.scrollTop(),
				mode = this.options.virtualizationMode;
			if (offset !== undefined) {
				if (dir === "down") {
					scrollContainer.scrollTop(current + offset);
				} else {
					scrollContainer.scrollTop(current - offset);
				}
				current = scrollContainer.scrollTop();
			} /*else {
				// A.T. 31 March 2011 - fix for bug #70681
				// Please revise
				if (Math.abs(current - this._oldScrollTop) < this.options.avgRowHeight && current - this._oldScrollTop !== 0) {
					if (current > this._oldScrollTop) {
						scrollContainer.scrollTop(this._oldScrollTop + this.options.avgRowHeight);
					} else {
						scrollContainer.scrollTop(this._oldScrollTop - this.options.avgRowHeight);
					}
				}
			}
			*/
			/* this._startRowIndex = Math.ceil(scrollContainer.scrollTop() * this._totalRowCount / (scrollContainer[0].scrollHeight - scrollContainer[0].offsetHeight)); */
			scrollTopDiff = scrollContainer.scrollTop() - this._oldScrollTop;
			if (Math.abs(scrollTopDiff) < 5 && $.ig.util.isFF && !isIE) {
				return;
			}
			if (mode === undefined || mode === "") {
				mode = "continuous";
			}
			if (mode === "fixed") {
				newSri = Math.ceil(scrollContainer.scrollTop() / avgRowHeight);
				/* M.H. 29 Feb 2016 Fix for bug 211215: In Internet Explorer, last rows are not
				displayed in igGrid when rowVirtualization is true and virtualizationMode is fixed. */
				/* When rows are over ~35K(it depends on avgRowHeight) - height of the scrollContainer becomes too big
				in IE max scroll top and max height of DIV is about 1.5 MLN pixels */
				if ($.ig.util.isIE) {
					scrollerHeight = this._getScrollContainerHeight();
					if (this._totalRowCount * avgRowHeight >= scrollerHeight + 2) {
						/* when scrolled to the bottom(ensure that it will be shown last records) */
						if (current + 5 > scrollerHeight - scrollContainer.innerHeight()) {
							newSri = this._getDataView().length - this._virtualRowCount;
						} else {
							newSri = Math.ceil((current / scrollerHeight) * this._totalRowCount);
						}
					}
				} else if (newSri === this._startRowIndex && this._virtualScrollMouseDown) {
					if (scrollTopDiff > 0 && scrollTopDiff < avgRowHeight) {
						newSri++;
						scrollContainer.scrollTop(scrollContainer.scrollTop() - scrollTopDiff + avgRowHeight);
					} else if (scrollTopDiff < 0 && Math.abs(scrollTopDiff) < avgRowHeight) {
						newSri--;
						scrollContainer.scrollTop(scrollContainer.scrollTop() - scrollTopDiff - avgRowHeight);
					}
				}
				if (newSri > this._totalRowCount - this._virtualRowCount) {
					newSri = this._totalRowCount - this._virtualRowCount;
				}
				if (newSri < 0) {
					newSri = 0;
				}
				/* it's important to throw pre-render before the rendering params are changed */
				if (newSri !== this._startRowIndex) {
					this._startRowIndex = newSri;
					this._renderVirtualRecords();
				}
			} else if (mode === "continuous") {
				// M.H. 19 Jun 2013 Fix for bug #142650: igGrid.virtualScrollTo API method is not working
				this._virtualScrollToInternal(current);
			}
			this._oldScrollTop = scrollContainer.scrollTop();
			this._oldDisplayContainerScrollTop = this._vdisplaycontainer().scrollTop();
			/* M.K. Preserve scroll position after dataBind */
			if (this._persistVirtualScrollTop) {
				this._saveFirstVisibleTRIndex();
			}
		},
		_scrollContainer: function () {
			if (!this._scrollContainerObj || this._scrollContainerObj.length === 0) {
				this._scrollContainerObj = this.container().find("#" + this.id() + "_scrollContainer");
			}
			return this._scrollContainerObj;
		},
		_onVirtualHorizontalScroll: function (event) {
			var newSci,
				internallyTriggered = event === undefined, //in the case of hiding or other features that would requred it
				horizontalScrollContainer = this._vhorizontalcontainer(),
				scrollLeft = horizontalScrollContainer.scrollLeft(),
				hiddenContentWidth = horizontalScrollContainer[ 0 ].scrollWidth -
				horizontalScrollContainer[ 0 ].offsetWidth;
			/* I.I. bug fix for 105932 and 105951 */
			if (this.options.virtualization === true && this.options.virtualizationMode === "continuous") {
				this._onScrollContainer(event);
				return;
			}
			this._isHorizontal = true;
			if (hiddenContentWidth > 0) {
				newSci = Math.ceil(scrollLeft * this._totalColumnCount / hiddenContentWidth);
				newSci = Math.min(newSci, this._totalColumnCount - this._virtualColumnCount);
			} else {
				newSci = 0;
			}
			if (internallyTriggered || newSci !== this._startColIndex) {
				this._startColIndex = newSci;
				this._renderVirtualRecords();
				/* trigger an event so that features that are header-dependent,
				such as filtering and sorting , update their UI accordingly */
				this._trigger("virtualhorizontalscroll", null,
					{
						startColIndex: this._startColIndex,
						endColIndex: this._startColIndex + this._virtualColumnCount - 1
					}
				);
			}
		},
		_initLoadingIndicator: function () {
			var widget;
			/* attach loading indicator widget */
			if (this.container().data("igLoading")) {
				this._loadingIndicator = this.container().data("igLoading").indicator();
			} else {
				widget = this.container().igLoading().data("igLoading");
				if (widget) {
					this._loadingIndicator = widget.indicator();
				}
			}
		},
		_addHorizontalScrollBar: function (parent) {
			this._outerHScrollbar = true;// there are cases when horizontal scrollbar is rendered outside of scroll container
			var sb = $("<div id=\"" + this.id() + "_hscroller_container\" ></div>")
				.css("height", this._scrollbarWidth() + "px")
				.css("position", "relative")
				.css("display", "none")
				.css("overflow", "hidden")
				.append(
					$("<div id=\"" + this.id() + "_hscroller\" ></div>")
						.data("containerName", "hScrollbar")
						.css("width", "100%")
						.css("position", "absolute")
						.css("bottom", "0px")
						.css("overflow-x", "scroll")
						.css("overflow-y", "scroll")
						.append(
							$("<div id=\"" + this.id() + "_hscroller_inner\" data-scroller ></div>")
								.css("height", "1px")
						)
						.bind("scroll", $.proxy(this._onScrollContainer, this))
				);
			if (parent) {
				sb.insertAfter(parent);
			}
			this._registerScrllCntnrToSync($("#" + this.id() + "_hscroller"));
		},
		_updateVirtualHorizontalScrollbar: function () {
			var horizontalScrollContainerInner, horizontalScrollContainer, isVisible;
			/* update horizontalScrollbar by updating horizontalScrollContainer inner div width */
			horizontalScrollContainerInner =
				this.container().find("#" + this.id() + "_horizontalScrollContainer div");
			horizontalScrollContainerInner.css("width", this._calculateContainerWidth(false));
			/* hide the horizontalScrollContainer if no scrollbar should be visible */
			horizontalScrollContainer = this._vhorizontalcontainer();
			/* M.H. 19 Feb 2015 Fix for bug #187756: When row selectors and fixed
			virtualization are enabled fixing a column moves the horizontal scrollbar downwards. */
			isVisible = horizontalScrollContainer.is(":visible");
			if (horizontalScrollContainer.width() > horizontalScrollContainerInner.width()) {
				horizontalScrollContainer.css("display", "none");
			} else {
				horizontalScrollContainer.css("display", "");
			}
			/* M.H. 19 Feb 2015 Fix for bug #187756: When row selectors and fixed
			virtualization are enabled fixing a column moves the horizontal scrollbar downwards. */
			if (isVisible !== horizontalScrollContainer.is(":visible")) {
				this._initializeHeights();
			}
		},
		_generateColumnFlatStructure: function (treeStructure) {
			var cols, oldCols, newCols = [];
			cols = treeStructure.slice(0);
			oldCols = treeStructure.slice(0);
			this._multiColumnIdentifier = 0;
			this._maxLevel = this._getMaxLevelRecursive(0, cols);
			this._hiddenColumns = {};
			this._analyzeMultiColumnHeaders(cols, newCols, 0, oldCols, []);
			this._oldCols = oldCols;
			this.options.columns = newCols;
		},
		moveColumn: function (column, target, after, inDom, callback) {
			/* Moves a visible column at a specified place, in front or behind a target column or at a target index
			Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
			paramType="number|string" An identifier of the column to be moved. It can be a key, a Multi-Column Header identificator, or an index in a number format. The latter is not supported when the grid contains multi-column headers.
			paramType="number|string" An identifier of a column where the moved column should move to or an index at which the moved column should be moved to. In the case of a column identifier the column will be moved after it by default.
			paramType="bool" optional="true" Specifies whether the column moved should be moved after or before the target column. This parameter is disregarded if there is no target column specified but a target index is used.
			paramType="bool" optional="true" Specifies whether the column moving will be enacted through DOM manipulation or through rerendering of the grid.
			paramType="function" optional="true" Specifies a custom function to be called when the column is moved.
			*/
			var grid = this, found, nColArray, movingParams, hcPreserve, isFixed,
				cCols = this._oldCols ? jQuery.extend(true, [], this._oldCols) :
				jQuery.extend(true, [], this.options.columns);
			/* Assume optional params */
			after = after === null || after === undefined ? true : after;
			inDom = inDom === null || inDom === undefined ? true : inDom;
			/* The first thing to do is move the columns in their internal collection wrappers. For a flat column layout
			we'll use this.options.columns and for a multicolumn layout we'll use _oldCols and then force an update
			on the columns collection. we also need to normalize the column and target params */
			movingParams = {
				column: column,
				target: target,
				after: after
			};
			if (this._oldCols) {
				found = this._performInternalMove(movingParams, this._oldCols);
				/* and update the column defs while preserving the hidden columns array */
				hcPreserve = jQuery.extend(true, {}, this._hiddenColumns);
				this._generateColumnFlatStructure(this._oldCols);
				this._hiddenColumns = hcPreserve;
				this._preserveColspans(this._oldCols);
			} else {
				found = this._performInternalMove(movingParams, this.options.columns);
			}
			/* If we couldn't accomplish the move on an internal level there is
			no point going on with actual DOM manipulations or grid rendering. */
			if (found === false) {
				throw new Error($.ig.Grid.locale.movingNotAllowedOrIncompatible);
			}
			if (movingParams.columnFixed === movingParams.targetFixed) {
				/*	The nColArray is a two dimensional array that consists of column id-s (or multicolumn header ids) and the number -1
					It normalizes the column layout where the ids represent actual columns and -1s represent absent columns due to
					upper columns covering the space through rowspan > 1
					This array is crucial for column moving as moving needs to be done in depth when we are moving multicolumn headers and
					the lower levels have complex structures involving rowspans and smaller colspans (an MCH inside an MCH)
					By simplifying the structure to this array the actual move can be accomplished via iteration as opposed to a complex
					constructive recursion logic.
					To avoid possible sync issues we are going to build the array on each run (as opposed to using a cached version)
					A cached version would need to get updated everytime this method is called anyway.	*/
				nColArray = this._buildColumnLayoutArray(cCols, movingParams.columnFixed);
			} else {
				// if the column and target are in different grids we'll return the
				// moving params so that the caller can handle the situation
				return movingParams;
			}
			/* update params */
			column = movingParams.column;
			target = movingParams.target;
			after = movingParams.after;
			isFixed = movingParams.columnFixed;
			if (inDom === true) {
				// proceed with the dom column move, it'll be done through a loading indicator and a seperate thread
				this._loadingIndicator.show();
				setTimeout(function () {
					grid._columnMovingResets();
					grid._performDomColumnMove(column, target, after, nColArray, isFixed);
					/* M.H. 4 Sep 2013 Fix for bug #144735: Right aligned last column content hides under scrollbar */
					grid._updateVerticalScrollbarCellPadding(true);
					grid._loadingIndicator.hide();
					if (callback) {
						$.ig.util.invokeCallback(callback, [ grid.options.columns ]);
					}
				}, 0);
			} else {
				this._columnMovingResets();
				this._performColumnMove(column, target, after, nColArray, isFixed);
				if (callback) {
					$.ig.util.invokeCallback(callback, [ grid.options.columns ]);
				}
			}
		},
		_columnMovingResets: function () {
			var i, $th;
			this._updateHeaderColumnIndexes();
			delete this._virtualDom;
			delete this._visibleColumnsArray;
			this._headerCells = [];
			for (i = 0; i < this.options.columns.length; i++) {
				$th = this.container().find("#" + this.id() + "_" +
					this.options.columns[ i ].key).data("columnIndex", i);
				$th.data("data-mch-order", i);
				/* M.H. 30 May 2016 Fix for bug 219935: Column Resizing does not work when multi column header and column fixing are enabled, and grid is initialized while it is invisible */
				if ($th.length && $th[ 0 ].style.display !== "none" &&
					$th[ 0 ].style.visibility !== "hidden" && $th.parent().is("tr")) {
					this._headerCells.push($th);
				}
			}
		},
		_preserveColspans: function (cols) {
			var i, cs = 0, col, res;
			for (i = 0; i < cols.length; i++) {
				col = cols[ i ];
				if (col.group !== undefined && col.group !== null) {
					res = this._preserveColspans(col.group);
					col.colspan = res;
					cs += res;
				} else {
					if (col.hidden !== true) {
						cs++;
					}
				}
			}
			return cs;
		},
		_columnVisible: function (col) {
			return !col.hidden;
		},
		_buildColumnLayoutArray: function (cCols, fixed) {
			// fixed specifies if the nColArray should be built for the unfixed or fixed grid
			var i = 0,
				j = 0,
				col,						// a single col in the cycle
				id,							// the single col's identification
				colrs,						// the single col's rowspan
				l,							// current col's horizontal length (colspan)
				x = 0,						// current horizontal identificator
				nCols,						// the newly built level
				level = 0,					// level number
				colgrp = fixed ? this.fixedBodyContainer().find("colgroup:first") :
				this.element.find("colgroup:first"),
				width = colgrp.children("col:not([data-skip=true])").length,
				htbl = fixed ? this.fixedHeadersTable() : this.headersTable(),
				height = htbl.find("thead tr").length,
				array = [];					// the array we are going to build
			if (height === 0 && this.options.showHeader === false) {
				height = 1;
			}
			for (i = 0; i < width; i++) {
				array[ i ] = [];
			}
			i = 0;
			while (level < height) {
				nCols = [];
				while (i < cCols.length) {
					col = cCols[ i ];
					if (col.hidden === true || this._isSubsetFixed(col) !== fixed) {
						i++;
						continue;
					}
					colrs = col.rowspan || 1;
					id = this._getColMarkForLevel(col);
					l = col.colspan || 1;
					for (j = 0; j < l; j++) {
						array[ x + j ][ level ] = id;
					}
					if (col.crs === colrs && col.group) {
						// we'll only preserve places for visible columns from the group
						nCols.push.apply(nCols, $.grep(col.group, this._columnVisible).slice(0));
					} else {
						nCols.push(col);
					}
					x += l;
					i++;
				}
				cCols = nCols;
				level++;
				x = 0;
				i = 0;
			}
			return array;
		},
		_getColMarkForLevel: function (col) {
			if (!col.crs) {
				col.crs = 0;
			}
			col.crs++;
			if (col.crs > 1) {
				return -1;
			}
			return col.key || col.identifier;
		},
		_performInternalMove: function (movingParams, subset) {
			/*	recursively searches for the required column and performs a move on the level the column was found
				returns true if the move is successful
			*/
			var i = 0, j, success = true;
			i = this._getColIdxById(subset, movingParams.column);
			if (typeof movingParams.column === "number") {
				movingParams.column = subset[ i ].key || subset[ i ].identifier;
			}
			if (i || i === 0) {
				// column is found specify if it's fixed
				movingParams.columnFixed = this._isSubsetFixed(subset[ i ]);
				j = this._getColIdxById(subset, movingParams.target);
				/* modify target when using indexes */
				if (typeof movingParams.target === "number") {
					movingParams.target = subset[ j ].key || subset[ j ].identifier;
					movingParams.after = j >= i;
				}
				if (j || j === 0) {
					// target is found specify if it's fixed
					movingParams.targetFixed = this._isSubsetFixed(subset[ j ]);
					delete subset[ i ].oWidth;
					delete subset[ j ].oWidth;
					success = success && this._rearrangeArray(
						subset,
						i,
						1,
						movingParams.after === true ? j + 1 : j
					);
					success = success || (movingParams.targetFixed !== movingParams.columnFixed);
					return success;
				}
				return false;
			}
			for (i = 0; i < subset.length; i++) {
				if (subset[ i ].group) {
					if (this._performInternalMove(movingParams, subset[ i ].group) === true) {
						return true;
					}
				}
			}
			return false;
		},
		_getColIdxById: function (array, id) {
			var i, col;
			if (typeof id === "number") {
				return id;
			}
			for (i = 0; i < array.length; i++) {
				col = array[ i ];
				if (col.key) {
					if (col.key === id) {
						return i;
					}
				} else if (col.identifier) {
					if (col.identifier === id) {
						return i;
					}
				}
			}
		},
		_isSubsetFixed: function (subset) {
			var mchc;
			if (subset.group) {
				mchc = this._getMultiHeaderColumnById(subset.identifier);
				return !!mchc.children[ 0 ].fixed;
			}
			return !!subset.fixed;
		},
		_getCellIndexByColumnKey: function (key) {
			// gets the index of the cell corresponding to a specific col key
			var i = this.getVisibleIndexByKey(key);
			return i === -1 ? i : i +
				this.element.find("tbody>tr:not([data-grouprow='true']):first")
				.children("th,td[data-skip='true'],td[data-parent]").length;
		},
		_findColAreaInLayout: function (col, nColArray, depth) {
			// searches for the column in the column layout array at a specified depth
			var i, j, res = {}; // the result is stored in a object with start and length properties
			for (i = 0; i < nColArray.length; i++) {
				if (nColArray[ i ][ depth ] === col) {
					res.start = i;
					for (j = i; j < nColArray.length; j++) {
						if (nColArray[ j ][ depth ] !== col) {
							break;
						}
					}
					res.length = j - i;
					return res;
				}
			}
			return null;
		},
		_rearrangeArray: function (array, start, length, at) {
			// rearranges an array by first removing the area from a specified start index and with a specified length
			// and then inserting it at a specified index
			var col, targetAfter = start < at, n;
			/* targetAfter stores whether the target position for the column is to the
			right of the current position as we need to modify the target position in this case */
			if (start === at || start < 0 || at < 0 || start >= array.length || at > array.length) {
				//dummy check
				return false;
			}
			col = array.splice(start, length);
			for (n = 0; n < col.length; n++) {
				array.splice(targetAfter ? at - length + n : at + n, 0, col[ n ]);
			}
			return true;
		},
		_moveColumnInHeader: function (column, target, after, nColArray, fixed) {
			var rmil,			// result move in layout (stores the start and length parameters for the moved and target columns)
				movedColumn,	// an object storing the start and length params of the moved column area at the data level
				targetColumn,	// an object storing the start and length params of the target column area at the data level
				targetIndex,	// an index at which the column which will be used as a moving end point resides
				targetObject,	// an object storing more complex information about the moving end point in the cases where it needs to be
								// additonally calculated
				spStart,		// search parameter start (helper var)
				spEnd,			// search parameter end (helper var)
				i,
				j,
				cols,
				n,
				header;
			if (fixed) {
				header = this.fixedHeadersTable().children("thead");
			} else {
				header = this.headersTable().children("thead");
			}
			/* find moved and target columns in the column layout array */
			for (j = 0; j < nColArray[ 0 ].length; j++) {
				movedColumn = this._findColAreaInLayout(column, nColArray, j);
				if (movedColumn) {
					targetColumn = this._findColAreaInLayout(target, nColArray, j);
					if (targetColumn) {
						break;
					}
				}
			}
			/* create the rmil array */
			rmil = [ movedColumn, targetColumn ];
			if (this.options.showHeader === false) {
				// if the header is not shown we just need to return this result so the table
				// columns can be moved
				return rmil;
			}
			/* then we start moving the actual dom elements, initial target first */
			this._moveThs({ from: header }, column, target, after);
			/* afterwards we'll search each level below and determine how
			to move other columns so the grid's layout remains intact */
			while (++j < nColArray[ 0 ].length) {
				cols = [];
				n = null;
				/* we will get all real columns (not marked as -1) for the current
				level which resides under the area marked by the initial moved column */
				for (i = movedColumn.start; i < movedColumn.start + movedColumn.length; i++) {
					if (nColArray[ i ][ j ] === -1) {
						continue;
					}
					if (nColArray[ i ][ j ] !== n) {
						n = nColArray[ i ][ j ];
						cols.push(n);
					}
				}
				/* next we find a proper target to use as an end point for the dom manipulation
				in the best case scenario we can find it simply by getting the columns at the edges of the target area */
				targetIndex = after === true ?
					targetColumn.start + targetColumn.length - 1 : targetColumn.start;
				/* if we find a column in this way we proceed with the actual dom manipulation */
				if (nColArray[ targetIndex ][ j ] !== -1) {
					this._moveThs({ from: header }, cols, nColArray[ targetIndex ][ j ], after);
					continue;
				}
				/* otherwise we need to try and find such a column either outside the target area or further inside it */
				if (after === true) {
					/* the index of the first element to the right */
					spStart = targetColumn.start + targetColumn.length;
					/* the index of the last element to be investigated, it depends
					on the relative position between the moved and the target columns */
					spEnd = movedColumn.start > targetColumn.start + targetColumn.length - 1 ?
						movedColumn.start : nColArray.length;
					/* search to the right first */
					targetObject = this._findTargetRight(spStart, spEnd, j, nColArray);
					if (!targetObject) {
						// if we couldn't find a good end point to the right we'll search to the left
						// the index of the first element to the left of the target's area end
						spStart = targetColumn.start + targetColumn.length - 2;
						/* either the start of the array or the end of the moved column's area */
						spEnd = movedColumn.start > targetColumn.start + targetColumn.length - 1 ?
							-1 : movedColumn.start + movedColumn.length;
						targetObject = this._findTargetLeft(spStart, spEnd, j, nColArray);
					}
				} else {
					// here we do just the same as above but since *after* is false the calculations differ
					spStart = targetColumn.start - 1;
					spEnd = movedColumn.start > targetColumn.start + targetColumn.length - 1 ?
						-1 : movedColumn.start + movedColumn.length;
					targetObject = this._findTargetLeft(spStart, spEnd, j, nColArray);
					if (!targetObject) {
						spStart = targetColumn.start + 1;
						spEnd = movedColumn.start > targetColumn.start + targetColumn.length - 1 ?
							movedColumn.start : nColArray.length;
						targetObject = this._findTargetRight(spStart, spEnd, j, nColArray);
					}
				}
				/* S.S. December 12, 2012 - The function is iterative and very fast but also filled with somewhat harder to follow logic.
				The best way to understand it is to imagine the column layout broken to a matrix (the nColArray), where we represent the forbidden
				fields with the number -1 and the normal fields with column keys (or mch id-s). At this point we already know the id of the column
				we want to move and the id of the column that it'll be inserted after/before. Therefore we can find the level we make the moving
				operation on and know that we need to do similar operations on each level below that.
				For each level below the initial one we grab all columns that reside under the column that was moved (or skip the level if
				there are none). And then find where to move them. It's simple process of traversing the array's level horizontally searching
				for a column id to perform the dom operations with. The only complexity comes from having to do it in a specific way so that
				we always know that the first column that we can use is going to provide us with the expected result. The issue really comes from
				the fact that the target area might be filled with forbidden fields which means the space is covered by a column at a higher level
				with a rowspan attribute. We need to find another column at that level, outside the target area to process the dom operation with.
				*/
				if (targetObject && targetObject.pos &&
					targetObject.left !== undefined && targetObject.left !== null) {
					// if we managed to find a column this way we'll proceed with a dom manipulation, otherwise the column layout is such that
					// this level does not require any operation
					this._moveThs(
						{ from: header },
						cols,
						nColArray[ targetObject.pos ][ j ],
						targetObject.left
					);
				}
			}
			return rmil;
		},
		_moveColumnInBodyFooter: function (area, movedColumn, targetColumn, after) {
			// uses the data level parameters found by moving in the header to move columns in the body and footer
			var i, j, trs, $tr, tds, tar, skip = 0, trsTo, $trTo, children, len,
				areaFrom = area.from || area;
			/* get all data rows in the target area (TBODY or TFOOT) if trsFrom i set
			(because of performance issues) use it(in columnFixing we do not need of selectors :not() - which is too slow) */
			trs = area.trsFrom ||
				areaFrom.find(">tr:not([data-container='true'],[data-grouprow='true'])");
			if (area.to || area.trsTo) {
				trsTo = area.trsTo ||
					area.to.find(">tr:not([data-container='true'],[data-grouprow='true'])");
			}
			if (trs.length > 0 && !area.ignoreDataSkip) {
				// skip equals the amount of special TDs or THs in the row that should not participate in the move operation
				skip = trs.eq(0).find("[data-parent],[data-skip='true'],th").length;
			}
			len = trs.length;
			for (j = 0; j < len; j++) {
				// we'll be filling a jquery collection of TDs to move
				tds = $();
				$tr = trs.eq(j);
				$trTo = trsTo ? trsTo.eq(j) : $tr;
				children = $tr.children();
				/* foreach TD in the moved area add the TD to the collection */
				for (i = movedColumn.start; i < movedColumn.start + movedColumn.length; i++) {
					tds = tds.add(children.eq(i + skip));
				}
				if (targetColumn.start === -1) {
					tds.appendTo($trTo);
					/* $trTo.append(tds); */
					continue;
				}
				/* then get an end point TD to perform the dom manipulation */
				if (after === true) {
					tar = $trTo.children().eq(targetColumn.start + targetColumn.length + skip - 1);
					tds.insertAfter(tar);
				} else {
					tar = $trTo.children().eq(targetColumn.start + skip);
					tds.insertBefore(tar);
				}
			}
		},
		_findTargetRight: function (start, end, level, nColArray) {
			// traverses the nColArray from left to right at a specified level searching for a defined column
			var i, colFound = {};
			for (i = start; i >= 0 && i < end && i < nColArray.length; i++) {
				if (nColArray[ i ][ level ] !== -1) {
					colFound.pos = i;
					colFound.left = false;
					return colFound;
				}
			}
		},
		_findTargetLeft: function (start, end, level, nColArray) {
			// traverses the nColArray from left to right at a specified level searching for a defined column
			var i, colFound = {};
			for (i = start; i >= 0 && i > end && i < nColArray.length; i--) {
				if (nColArray[ i ][ level ] !== -1) {
					colFound.pos = i;
					colFound.left = true;
					return colFound;
				}
			}
		},
		_moveCols: function (area, movedColumn, targetColumn, after) {
			// moves cols in the colgroup of the specified area
			var i, cols = $(), tCol, areaFrom = area.from || area, areaTo = area.to || areaFrom,
				skip = !area.ignoreDataSkip ? areaFrom.children("[data-skip='true']").length : 0;
			for (i = movedColumn.start; i < movedColumn.start + movedColumn.length; i++) {
				cols = cols.add(areaFrom.children().eq(i + skip));
			}
			if (targetColumn.start === -1) {
				cols.detach().appendTo(areaTo);
				return;
			}
			/* get an end point COL to perform the dom manipulation */
			if (after === true) {
				tCol = areaTo.children().eq(targetColumn.start + targetColumn.length + skip - 1);
				cols.detach().insertAfter(tCol);
			} else {
				tCol = areaTo.children().eq(targetColumn.start + skip);
				cols.detach().insertBefore(tCol);
			}
		},
		_moveThs: function (header, ids, tar, after) {
			// moves THs in the provided header
			var $tar, $ids = $(), $id, i, $pid,
				headerFrom = header.from || header, headerTo = header.to || headerFrom;
			/* holds the TH containing the padding for the vertical scrollbar(last TH) */
			/* find the move end point - the following selector always returns a single TH as THs either
			contain an id attribute (data THs) or an data-mch-id attributed (MCH THs) */
			$tar = headerTo.find("th[data-mch-id='" + tar +
				"'],th[id='" + this.id() + "_" + tar + "']");
			/* the ids param is either an array or a simple value */
			if (typeof ids === "object") {
				for (i = 0; i < ids.length; i++) {
					$id = headerFrom.find("th[data-mch-id='" + ids[ i ] +
						"'],th[id='" + this.id() + "_" + ids[ i ] + "']");
					if ($id.attr("data-vscr-padding-icrement")) {
						$pid = $id;
					}
					$ids = $ids.add($id);
				}
			} else {
				$ids = headerFrom.find("th[data-mch-id='" + ids +
					"'],th[id='" + this.id() + "_" + ids + "']");
				if ($ids.attr("data-vscr-padding-icrement")) {
					$pid = $ids;
				}
			}
			/* we detach all the THs we found */
			$ids.detach();
			/* and attach them after the target */
			if (after) {
				$ids.insertAfter($tar);
			} else {
				$ids.insertBefore($tar);
			}
			/* afterwards we need to assign the padding of the
			previously last column to the newly last column (if needed) */
			if ($tar.attr("data-vscr-padding-icrement") && after === true) {
				$ids
					.last()
					.css(this._padding, $tar.css(this._padding))
					.attr("data-vscr-padding-icrement", $tar.attr("data-vscr-padding-icrement"));
				$tar.css(this._padding, "");
				$tar.removeAttr("data-vscr-padding-icrement");
			} else if ($pid) {
				$pid
					.parent()
					.children(":last")
					.css(this._padding, $pid.css(this._padding))
					.attr("data-vscr-padding-icrement", $pid.attr("data-vscr-padding-icrement"));
				$pid.css(this._padding, "");
				$pid.removeAttr("data-vscr-padding-icrement");
			}
		},
		_moveSpecialThs: function (movedColumn, targetColumn, after, fixed) {
			// moves elements contained in special header rows (such as the Filter row)
			var i, j, spTrs, header, skip, ths, $tr, tar;
			if (fixed) {
				header = this.fixedHeadersTable().children("thead");
			} else {
				header = this.headersTable().children("thead");
			}
			spTrs = header.find(">tr[data-role]");
			if (spTrs.length > 0) {
				skip = spTrs.eq(0).find("[data-parent],[data-skip='true']").length;
			}
			for (j = 0; j < spTrs.length; j++) {
				ths = $();
				$tr = $(spTrs[ j ]);
				for (i = movedColumn.start; i < movedColumn.start + movedColumn.length; i++) {
					ths = ths.add($tr.children("td,th").eq(i + skip));
				}
				if (after === true) {
					tar = $tr.children().eq(targetColumn.start + targetColumn.length + skip - 1);
					ths.detach().insertAfter(tar);
				} else {
					tar = $tr.children().eq(targetColumn.start + skip);
					ths.detach().insertBefore(tar);
				}
			}
		},
		_performDomColumnMove: function (column, target, after, nColArray, fixed) {
			var rmil = this._moveColumnInHeader(column, target, after, nColArray, fixed), /* moving the header also produces
				information about how to perform the operation on the TBODY/TFOOT	*/
				movedColumn = rmil[ 0 ],
				targetColumn = rmil[ 1 ],
				body, footer, indexMod = 0;
			/* rearrange header cells positionined in special header rows */
			this._moveSpecialThs(movedColumn, targetColumn, after, fixed);
			/* rearrange the cols for the header if fixedHeaders is enabled */
			if (this.options.fixedHeaders === true) {
				this._moveCols({
					from: fixed ? this.fixedHeadersTable().children("colgroup") :
						this.headersTable().children("colgroup")
				}, movedColumn, targetColumn, after);
			}
			/* and finally rearrange the body */
			if (fixed) {
				body = this.fixedBodyContainer().children("table");
			} else {
				body = this.element;
			}
			this._moveColumnInBodyFooter({
				from: body.children("tbody")
			}, movedColumn, targetColumn, after);
			/* its colgroup */
			this._moveCols({
				from: body.children("colgroup")
			}, movedColumn, targetColumn, after);
			/* the footers */
			if (this.options.fixedFooters === true) {
				//footer = $("#" + this.id() + "_footers");
				footer = fixed ? this.fixedFootersTable() : this.footersTable();
				this._moveColumnInBodyFooter({
					from: footer.children("tfoot")
				}, movedColumn, targetColumn, after);
				this._moveCols({
					from: footer.children("colgroup")
				}, movedColumn, targetColumn, after);
			} else {
				this._moveColumnInBodyFooter({
					from: body.children("tfoot")
				}, movedColumn, targetColumn, after);
			}
			/* when the dom has changed we need to throw an event
			to notify other features to update dom related parameters */
			if ((!fixed && this.fixingDirection() === "left") ||
				(fixed && this.fixingDirection() === "right")) {
				indexMod = this._fixedColumns ? this._fixedColumns.length : 0;
			}
			this._trigger("_columnsmoved", null, {
				owner: this,
				start: movedColumn.start + indexMod,
				len: movedColumn.length,
				index: after === true ?
					targetColumn.start + targetColumn.length + indexMod :
					targetColumn.start + indexMod,
				isFixed: fixed
			});
		},
		_performColumnMove: function (column, target, after, nColArray, fixed) {
			var rmil = this._moveColumnInHeader(column, target, after, nColArray, fixed),
				movedColumn = rmil[ 0 ],
				targetColumn = rmil[ 1 ];
			/* rearrange the cols for the header if needed */
			if (this.options.fixedHeaders === true) {
				this._moveCols({
					from: fixed ?
						this.fixedHeadersTable().children("colgroup") :
						this.headersTable().children("colgroup")
				}, movedColumn, targetColumn, after);
			}
			this._renderData();
			this._renderFooter();
			this._rerenderColgroups();
		},
		showColumn: function (column, callback) {
			/* Shows a hidden column. If the column is not hidden the method does nothing.
				Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
				paramType="number|string" An identifier for the column. If a number is provided it will be used as a column index. If a string is provided it will be used as a column key.
				paramType="function" Specifies a custom function to be called when the column is shown(optional)
			*/
			var grid = this;
			this._loadingIndicator.show();
			if (!this._isShowingAllowed([ column ])) {
				return false;
			}
			setTimeout(function () {
				var col;
				col = grid._setHidden(column, false);
				grid._loadingIndicator.hide();
				if (callback) {
					$.ig.util.invokeCallback(callback, [ [ col ], false ]);
				}
			}, 0);
			return true;
		},
		hideColumn: function (column, callback) {
			/* Hides a visible column. If the column is hidden the method does nothing.
				Note: This method is asynchronous which means that it returns immediately and any subsequent code will execute in parallel. This may lead to runtime errors. To avoid them put the subsequent code in the callback parameter provided by the method.
				paramType="number|string" An identifier for the column. If a number is provided it will be used as a column index else if a string is provided it will be used as a column key.
				paramType="function" Specifies a custom function to be called when the column is hidden(optional)
			*/
			var grid = this;
			if (!this._isHidingAllowed([ column ])) {
				return false;
			}
			/* M.H. 6 Nov 2014 Fix for bug #184605: TypeError is thrown if
			all the columns are hidden from igGridHiding’s columnSettings option. */
			if (grid._visibleColumns().length === 1) {
				//hiding the last column through the API should not be allowed
				return false;
			}
			this._loadingIndicator.show();
			setTimeout(function () {
				var col;
				col = grid._setHidden(column, true);
				grid._loadingIndicator.hide();
				if (callback) {
					$.ig.util.invokeCallback(callback, [ [ col ], true ]);
				}
			}, 0);
			return true;
		},
		_setHidden: function (column, hidden) {
			var col, applied = false;
			if (typeof column === "number") {
				col = this.options.columns[ column ];
			} else {
				col = this.columnByKey(column);
			}
			/* perform hiding/showing if the hidden value actually changed */
			if (col && col.hidden !== hidden) {
				this._setHiddenColumns([ col ], hidden, false);
				applied = true;
			}
			if (applied) {
				return col;
			}
			return null;
		},
		_visibleAreaWidth: function (w) {
			// get or sets minimal visible area width
			if (w !== undefined) {
				this._minVAreaWidth = w;
			} else {
				return this._minVAreaWidth;
			}
		},
		_isShowingAllowed: function (columns) {
			// check whether showing columns is allowed - it should NOT be allowed if columns are fixed and showing them will increase the width of the fixed visible area more than the whole grid container
			if (!this.hasFixedColumns()) {
				return true;
			}
			var i, columnsLength = columns.length, totalW = 0, w, gridW, colType, col;

			for (i = 0; i < columnsLength; i++) {
				colType = (typeof columns[ i ]);
				if (colType === "string") {
					col = this.columnByKey(columns[ i ]);
				} else if (colType === "number") {
					col = this.options.columns[ columns[ i ] ];
				} else {
					col = columns[ i ];
				}
				if (!col || !col.hidden) {
					continue;
				}
				if (col.fixed && (col.width || col.oWidth)) {
					w = col.width || col.oWidth;
					w = parseInt(w, 10);
					totalW += w;
				}
			}
			/* in case we show fixed columns */
			if (totalW !== 0) {
				gridW = this.container().outerWidth();
				if (gridW - parseInt(this.fixedContainer().outerWidth(), 10) - totalW <
					this._visibleAreaWidth()) {
					return false;
				}
			}
			return true;
		},
		_isHidingAllowed: function (columns) {
			// Check whether hiding is allowed(it is made when there are fixed columns, otherwise returns true).
			// It can't be allowed when all columns in fixed or unfixed area are hidden
			// columns is array. It could be array of column indexes, column keys, or column objects(or mixed of these three)
			if (!this.hasFixedColumns()) {
				return true;
			}
			var i, columnsLength = columns.length, col, colType,
				fixed = [], unfixed = [];

			for (i = 0; i < columnsLength; i++) {
				colType = (typeof columns[ i ]);
				if (colType === "string") {
					col = this.columnByKey(columns[ i ]);
				} else if (colType === "number") {
					col = this.options.columns[ columns[ i ] ];
				} else {
					col = columns[ i ];
				}
				if (!col || col.hidden) {
					continue;
				}
				if (col.fixed) {
					fixed.push(col);
				} else {
					unfixed.push(col);
				}
			}
			if ((fixed.length !== 0 && this._visibleColumns(true).length <= fixed.length) ||
				this._visibleColumns(false).length <= unfixed.length) {
				return false;
			}
			return true;
		},
		_setHiddenColumns: function (columns, hidden, initial) {
			var columnNumberChanged = false, visibleColumnsWithWidthLength, gridWidth,
				self = this, inPerc, visibleColumnsLength,
				hasVirtualization = this.options.virtualization === true ||
				this.options.columnVirtualization === true ||
				this.options.rowVirtualization === true,
				hasColumnVirtualization = (this.options.virtualizationMode !== "continuous") &&
				((this.options.virtualization === true && this.options.width) ||
				this.options.columnVirtualization === true),
				isContinuos = false;
			if (columns.length === 0) {
				return;
			}
			/* updating should remove AddNewRow objects, and _hidingFinished is too late */
			this._fireInternalEvent("_hidingFinishing", { columns: columns, hidden: hidden });
			if (hasVirtualization) {
				// M.H. 13 Sep 2012 Fix for bug #120220
				isContinuos = (this.options.virtualizationMode === "continuous");
				self._updateVirtColCounters();
				if (!isContinuos) {
					$.each(columns, function (index, col) {
						var oldVirtualColumnCount = self._virtualColumnCount;
						col.hidden = hidden;
						self._visibleColumnsArray = undefined;
						self._updateVirtColCounters();
						if (self._virtualColumnCount !== oldVirtualColumnCount) {
							if (self._virtualColumnCount < oldVirtualColumnCount) {
								self._detachColumn(col);
							} else if (self._virtualColumnCount > oldVirtualColumnCount) {
								self._attachColumn(col);
							}
							columnNumberChanged = true;
							/* force rebuild of _virtualDom */
							self._resetVirtualDom();
						}
					});
					if (hasColumnVirtualization) {
						//if we have column virtualization a horizontal scroll triggering is required
						this._vheaders = undefined;
						this._updateVirtualHorizontalScrollbar();
						/* M.H. 12 Sep 2012 Fix for bug #120220 */
						this._onVirtualHorizontalScroll();
						/* M.H. 12 Sep 2013 Fix for bug #146743: Columns are misaligned when
						columnVirtualization = true and there is an initially hidden column */
						if (!this._initialized) {
							this._adjustLastColumnWidth(true);
						}
					}
					this._renderVirtualRecords();
				}
			}
			/* M.H. 13 Sep 2012 Fix for bug #120220 */
			if (!hasVirtualization || isContinuos) {
				$.each(columns, function (index, col) {
					var pos;
					col.hidden = hidden;
					self._visibleColumnsArray = undefined;

					if (col.hidden) {
						pos = self._detachColumn(col);
						self.element.find("colgroup>col").not("[data-skip]")
							.eq(pos).attr("data-hiding", true);
					} else {
						self._attachColumn(col);
						$("<col data-showing=\"true\">").appendTo(self.element.find("colgroup"));
					}
				});
				/* M.H. 20 Mar 2015 Fix for bug 190598: FeatureChooser retains incorrect state
				for child grids after the parent is re-rendered on touch device in case of
				hierarchical grid we should remove elements in expanded rows via empty().
				_cleanupTBody removes children elements using JavaScript function removeChild.
				If there are jQuery widgets instantiated on some of the child elements destroy
				will not be called - this could cause memory leaks. */
				if (this._isHierarchicalGrid) {
					this.element.children("tbody").children("tr[data-container]").empty();
				}
				/* clear tbody and render data */
				this._cleanupTBody();
				/* M.H. 25 Feb 2016 Fix for bug 214785: Row height is changed when hiding fixed columns */
				if (this.hasFixedColumns()) {
					this._rerenderColgroups();
				}
				columnNumberChanged = true;
				/* M.H. 13 Sep 2012 Fix for bug #120220 */
				if (hasVirtualization) {
					/* M.H. 20 Jan 2016 Fix for bug 212626: When you hide 2 columns
					and showanother column - clicking on Add new row throws JS error */
					/* this._virtualColumnCount - is used NOT ONLY when columnVirtualization
					isenabled BUT also when virtualizationMode is fixed(AFAIK in selection/updating
					is used even if rowVirtualization is contiunous) */
					this._updateVirtColCounters();
					if (this._initialized) {
						this._renderVirtualRecords();
					}
				} else {
					this._renderRecords();
				}
				/* M.H. 25 Sep 2015 Fix for bug 206738: Length of the data rows when
				hiding a multicolumn header in igGrid does not get updated in Mozilla Firefox */
				self.element.find("colgroup>col[data-showing]").remove();
				self.element.find("colgroup>col[data-hiding]").removeAttr("data-hiding");
			}
			if (columnNumberChanged) {
				if (!this._initialized) {
					// M.H. 6 Nov 2014 Fix for bug #184605: TypeError is thrown if all the columns are hidden from igGridHiding’s columnSettings option.
					if (this._visibleColumns().length === 0) {
						throw new Error($.ig.Grid.locale.allColumnsHiddenOnInitialization);
					}
					this._rerenderColgroups(initial);
					this._adjustLastColumnWidth(true);
					/* M.H. 24 July 2015 Fix for bug 203369: Grid layout breaks
					when grouping by some column and virtualization is enabled */
					if (hasVirtualization) {
						this._renderVirtualRecords();
					}
				} else {
					//adjust last column width and grid width only if we have columns
					//leave the grid in the same configuration otherwise
					if (this._visibleColumns().length > 0) {
						this._rerenderColgroups(initial);
						/* adjust the last column width after the data is rendered and we know if we need a scrollbar or not */
						this._adjustLastColumnWidth(true);
						/* adjust grid width */
						visibleColumnsLength = this._visibleColumns().length;
						visibleColumnsWithWidthLength =
							$.grep(this._visibleColumns(),
								function (col) {
									return col.width;
								}).length;
						/* adjust width only if all visible columns have width */
						if (visibleColumnsWithWidthLength === visibleColumnsLength) {
							if (this.options.width && parseInt(this.options.width, 10) > 0) {
								//if grid has width sum all visible columns' widths and set it as table width
								this._updateGridContentWidth();
							} else {
								//if grid has no width set the container width as the sum of all columns widths
								//if (this.options.width !== null) {
								//Bug 216021 - the container width needs to change when no width is set and
								//columns are hidden/shown.
								this._setContainerWidth(this.container());
								if (hasVirtualization) {
									//headerParent = $('#' + this.element[0].id + '_headers_v').css('width', width);
									gridWidth = this._calculateContainerWidth(true);
									if (this.options.height !== null) {
										gridWidth -= this._scrollbarWidth();
									}
									this._vdisplaycontainer()
										.css("width", gridWidth)
										.css("max-width", gridWidth);
								}
							}
						}
					}
				}
			}
			this.element.trigger("iggriduisoftdirty", { owner: this });
			this._trigger(this.events.columnsCollectionModified, null, { owner: this });
			/* M.H. 17 July 2012 Fix for bug #105203 - it should be called after
			columnsCollectionModified because special column width is calculated properly */
			if (columnNumberChanged &&
				hasVirtualization &&
				this._visibleColumns().length > 0 &&
				visibleColumnsWithWidthLength === visibleColumnsLength) {
				if (this.options.virtualizationMode === "continuous") {
					if (!isNaN(gridWidth)) {
						gridWidth += this._calculateSpecialColumnsWidth();
						this._vdisplaycontainer()
							.css("width", gridWidth)
							.css("max-width", gridWidth);
					}
				}
				if (!isNaN(gridWidth)) {
					this.container().find("#" + this.id() + "_virtualContainer>colgroup col")
						.eq(0).attr("width", gridWidth);
				}
				/* $("#' + this.element[0].id + '_headers_v').width() */
				/* M.H. 27 Aug 2012 Fix for bug #119710 - we should re-set
				width of the virtual container only when width is not set */
				if (this.options.width === null || this.options.width === undefined) {
					this._setContainerWidth(this.container().find("#" + this.id() + "_headers_v"));
				}
			}
			this._origWidth = parseInt(this.container().css("width"), 10);
			/* M.H. 31 Mar 2014 Fix for bug #167868: In Safari, IgniteUI grid header shifts right
			when the Filtering is enabled and the first column is initially hidden */
			if (columnNumberChanged && $.ig.util.isSafari && this.options.showHeader) {
				self.headersTable().find("[data-header-row]").hide();
				setTimeout(function () {
					self.headersTable().find("[data-header-row]").show();
					if (self.options.height) {
						self._initializeHeights();
					}
				}, 0);
			}
			if (columnNumberChanged && ($.ig.util.isFF || $.ig.util.isIE8)) {
				//in order to have columns aligned correctly on FF and IE8 detach/attach colgroups
				// M.H. 16 Dec 2013 Fix for bug #154835: In Internet Explorer 8 when the grid has 100% width,  filtering is enabled and a column is hidden there is a blank space on the right of the grid.
				inPerc = this.options.width === null ||
					(typeof this.options.width === "string" && this.options.width.indexOf("%") !== -1);
				if (inPerc) {
					if ($.ig.util.isIE8) {
						this._refreshUI();
					}
					/* M.H. 7 Jan 2014 Fix for bug #160871: Hiding through API is causing misalignment in Firefox */
					this._refreshTableUI(this.element);
					if (this.options.showHeader) {
						this._refreshTableUI(this.headersTable());
					}
					/* update the footers table if we have one */
					if (this.options.fixedFooters === true && this.options.height !== null) {
						this._refreshTableUI(this.footersTable());
					}
				}
			}
			/* M.H. 27 Mar 2015 Fix for bug 190057: Hiding a column causes
			the grid to change its height(height is explicitly set) */
			if (columnNumberChanged) {
				this._checkAndReinitializeContainersHeights();
			}
			this._fireInternalEvent("_hidingFinished", { columns: columns, hidden: hidden });
		},
		_refreshTableUI: function ($tbl) {
			// Firefox hack to force layout to refresh when hiding columns. Layout engine in Mozilla FF does not update width of the columns(set in col of colgroup) when hiding/showing is performed
			if (!$tbl.length) {
				return;
			}
			var overflow;
			/*, colgroup = $tbl.children("colgroup").detach();
			$tbl.prepend(colgroup); */
			overflow = $tbl[ 0 ].style.overflow;
			$tbl[ 0 ].style.overflow = "hidden";
			setTimeout(function () {
				$tbl[ 0 ].style.overflow = overflow;
			}, 0);
		},
		_checkAndReinitializeContainersHeights: function (initH) {
			/* If the height of the grid is set check whether grid outer container's height has the proper height - equal to the initH if set OR height of the grid's options.
			This check will not be done if grid has height set in percentage or it is not set - null/undefined/empty string
			*/
			var c;
			if (_aNull(this.options.height) ||
					!this.options.height ||
					($.type(this.options.height) === "string" && this.options.height.indexOf("%") > 0)) {
				return;
			}
			c = this.container();
			if (!c.length) {
				return;
			}
			if (_aNull(initH)) {
				initH = c.height();
			}
			/* in IE < 8 scrollHeight is not implemented properly and could not work
			in all cases - set the containers height to "" and get its height
			c.height("");
			if (c.height() !== initH) {
				reInitHeights = true;
			} */
			if (c[ 0 ].scrollHeight - initH > 1) {
				this._initializeHeights();
			}
		},
		_calculateSpecialColumnsWidth: function () {
			var width = 0, cols = $();
			if (this.hasFixedColumns() && this.fixingDirection() === "left") {
				cols = this.fixedHeadersTable().find("> colgroup > col[data-skip=true]");
			}
			this.headersTable()
				.find("> colgroup > col[data-skip=true]")
				.each(function () {
					cols = cols.add(this);
				});
			/* take the special columns widths into account */
			cols.each(function () {
				var colWidth = this.style.width;
				if (colWidth) {
					width += parseInt(colWidth, 10);
				}
			});
			return width;
		},
		_allSpecialColumnsInPercentage: function () {
			var specCols = this.headersTable().find("> colgroup > col[data-skip=true]"), i;
			for (i = 0; i < specCols.length; i++) {
				if (!specCols[ i ].style.width.endsWith("%")) {
					return false;
				}
			}
			return true;
		},
		_synchronizeHScroll: function () {
			var hasFixedHeaders = this.options.showHeader &&
				this.options.fixedHeaders === true &&
				this.options.height !== null,
				hasFixedFooters = this.options.showFooter &&
				this.options.fixedFooters === true &&
				this.options.height !== null,
				scroller = this._hscrollbarcontent(),
				scrollContainer = this.scrollContainer(),
				$vContainer, horizontalScrollContainer,
				scrLeft = scroller.scrollLeft(),
				headers, footers;
			/* M.H. 26 Jul 2013 Fix for bug #145572: When Resizing with continuous
			rowVirtualization no horizontal scrollbar appears when needed */
			if (scroller.length === 0) {
				/* M.H. 9 Sep 2014 Fix for bug #180207: Horizontal scroll position is
				reset on igGrid after the filter editor is closed and reopened */
				horizontalScrollContainer = $("#" + this.id() + "_horizontalScrollContainer");
				if (horizontalScrollContainer.length === 1) {
					scrLeft = horizontalScrollContainer.scrollLeft();
					this._vdisplaycontainer().scrollLeft(scrLeft);
				} else {
					return;
				}
			} else if (scrollContainer.length > 0) {
				scrollContainer.scrollLeft(scrLeft);
				/* when there is no data in scrollContainer then scrollContainer.scrollLeft() always returns 0 even if scrLeft is not 0 */
				/* M.H. 23 Apr 2014 Fix for bug #169582: Misalignment between the header
				and the footer when advanced filtering and continuous virtualization are enabled */
				if (scrollContainer.scrollLeft() !== 0 || this.element.height() !== 0) {
					scrLeft = scrollContainer.scrollLeft();
					scroller.scrollLeft(scrLeft);
				}
			} else {
				$vContainer = this._vdisplaycontainer();
				if ($vContainer.length) {
					$vContainer.scrollLeft(scrLeft);
				}
			}
			if (hasFixedHeaders) {
				headers = this.headersTable().parent();
				headers.scrollLeft(scrLeft);
			}
			if (hasFixedFooters) {
				footers = this._fixedfooters();
				footers.scrollLeft(scrLeft);
			}
		},
		_updateGridContentWidth: function () {
			var gridWidth = 0, hasWidthInPixels = this._gridHasWidthInPixels(),
				scroller,
				scrLeft,
				hasFixedHeader = this.options.height !== null &&
				this.options.fixedHeaders === true &&
				this.options.showHeader && this._headerParent;
			$.each(this._visibleColumns(), function (index, col) {
				if (col.fixed === true) {
					return true;
				}
				if (gridWidth !== undefined &&
						col.width &&
						!(col.width.charAt && col.width.endsWith("%"))) {
					gridWidth += parseInt(col.width, 10);
				} else {
					gridWidth = undefined;
				}
			});
			if (gridWidth === undefined) {
				return;
			}
			/* take the special columns widths into account */
			/* M.H. 6 Jun 2016 Fix for bug 220191: When rowselectors are enabled and grid width is greater than sum of column widths and autofitLastColumn is true horizontal scrollbar is shown */
			gridWidth += this.hasFixedColumns() && this.fixingDirection() === "left" ? 0 :
												this._calculateSpecialColumnsWidth();
			/* L.A. 05 June 2012. Fixing bug #113545 When grid's width is bigger than the sum of column widths
			and fixedHeaders=false the hidding icon of the last column is not visible. */
			/* M.H. 27 Oct 2014 Fix for bug #183041: After showing a hidden
			column in unfixed table - width of the table is not correct */
			if (!hasFixedHeader && this._hasVerticalScrollbar &&
				(!hasWidthInPixels || (hasWidthInPixels && gridWidth <= parseInt(this.options.width, 10)))) {
				gridWidth -= this._scrollbarWidth();
			}
			if (this.options.width !== null && this.options.height !== null) {
				scroller = this._hscrollbarcontent();
				scrLeft = scroller.scrollLeft();
			}
			this._setGridContentWidth(gridWidth);
			if (this.options.height !== null && this.options.width !== null &&
				!this.options.virtualization && !this.options.rowVirtualization &&
				!this.options.columnVirtualization) {
				this._updateVerticalScrollbarCellPadding();
			}
			if (this.options.width !== null && this.options.height !== null) {
				//A.Y. bug 91577. restore the scrolLeft of the grid as some browsers may calculate it as 0 when the table size is changed.
				scroller.scrollLeft(scrLeft);
				this._synchronizeHScroll();
			}
		},
		_updateVerticalScrollbarCellPadding: function (skipHeaderFooters) {
			//if there is a vertical scrollbar the right padding
			//of the last column's header and footer cells should be adjusted
			//such that the content is not over the scrollbar
			var o = this.options,
				hasFixedHeaders = o.showHeader &&
				o.fixedHeaders === true &&
				o.height !== null,
				hasFixedFooters = o.showFooter &&
				o.fixedFooters === true &&
				o.height !== null,
				emptySpace, paddingIncrement;
			if (o.height === null) {
				//if the grid has no height there is no vertical scrollbar
				return;
			}
			if (!hasFixedHeaders && !hasFixedFooters) {
				//if the grid doesn't hava fixed headers or footers the vertical scrollbar
				//is across the whole table, including the header and the footer,
				//and no padding adjustment is required
				return;
			}
			if (this._gridInnerWidth === undefined || this._gridContentWidth === undefined) {
				//if the grid's inner width or content width are undefined(not all columns have width in pixels)
				//then the grid content fills the whole grid
				emptySpace = 0;
			} else {
				emptySpace = this._gridInnerWidth - this._gridContentWidth;
			}
			if (this._hasVerticalScrollbar ||
				((o.virtualization || o.rowVirtualization) &&
				(typeof o.width === "string" && o.width.indexOf("%") > 0))) {
				paddingIncrement = this._scrollbarWidth();
			} else {
				paddingIncrement = 0;
			}
			if (emptySpace >= 0) {
				//if there is empty space it should be substracted from the padding
				paddingIncrement -= emptySpace;
			}
			if (paddingIncrement < 0) {
				paddingIncrement = 0;
			}
			this._updateVScrollbarCellPaddingHelper(paddingIncrement, skipHeaderFooters);
			return paddingIncrement;
		},
		_updateVScrollbarCellPaddingHelper: function (paddingIncrement, skipHeaderFooters) {
			var hasFixedHeaders = this.options.showHeader &&
				this.options.fixedHeaders === true &&
				this.options.height !== null,
				hasFixedFooters = this.options.showFooter &&
				this.options.fixedFooters === true &&
				this.options.height !== null;
			if (hasFixedHeaders && !skipHeaderFooters) {
				this._increaseLastHeaderCellVScrollbarPadding(this.headersTable(), paddingIncrement);
			}
			if (!skipHeaderFooters) {
				if (hasFixedFooters) {
					this._increaseLastCellVScrollbarPadding(this.footersTable(), "tfoot", "td", paddingIncrement);
				} else {
					this._increaseLastCellVScrollbarPadding(this.element, "tfoot", "td", paddingIncrement);
				}
			}
			this._increaseLastCellVScrollbarPadding(this.element, "tbody", "td", paddingIncrement);
		},
		/* M.H. 8 Apr 2015 Fix for bug 186558: When the grid has width, the indicators
		of a last column of a group receive indentation incorrectly */
		_getLastCellsInMCH: function (headerTable) {
			// get last(visible) cells from the Multi Column Headers
			// the problem: It is possible that last data cell of table row to be NOT the last visible data cell.
			// Example: There are 2 columns - the first is a group column which has 2 data columns, the second has one data column. Then 2 <TR> are rendered as A DOM - but the last visible row is the last child of the first <TR> - those with data-mch-level=1. The second <tr> has 2 children - the data cells of the first group column.
			// takes as an argument - headerTable - the table that contains the header cells  - if not specified takes it from this.headersTable()
			var $thead, ml = this._maxLevel, $tr, lastCells = $(), i, $th, rowspan;
			if (!headerTable) {
				headerTable = this.headersTable();
			}
			$thead = headerTable.find("thead");
			/* loop through the MCH rows - starting from the last row(with highest data-mch-level)
			if it has rowspan - go to the row with index which is the current index(i) - rowspan */
			if (!ml || !$thead.length) {
				return lastCells;
			}
			for (i = ml; i >= 0; i--) {
				$tr = $thead.children("tr[data-mch-level=" + i + "]");
				if (!$tr.length) {
					continue;
				}
				$th = $tr.children("th:last-child")
					.not("[data-skip=true]");
				lastCells = lastCells.add($th);
				rowspan = parseInt($th.attr("rowspan"), 10);
				if (!isNaN(rowspan) && rowspan > 1) {
					i -= (rowspan - 1);
				}
			}
			/* add (non-data) cells */
			lastCells = lastCells.add(
				$thead.children("tr:not([data-mch-level])")
				.not("[data-skip=true]")
				.children("th:last-child")
				.not("[data-skip=true]")
			);
			return lastCells;
		},
		_increaseLastHeaderCellVScrollbarPadding: function (table, paddingIncrement) {
			var lastCells, currIncrement, paddingValue, selector = "", i = 0;
			/* M.H. 8 Apr 2015 Fix for bug 186558: When the grid has width, the indicators
			of a last column of a group receive indentation incorrectly */
			if (this._isMultiColumnHeader) {
				lastCells = this._getLastCellsInMCH(table);
			} else if (this._isMultiRowGrid()) {
				for (i = 0; i < this._rlm.length; i++) {
					selector += "#" + this.id() + "_" + this._rlm[ i ][ this._maxCols - 1 ] + ", ";
				}
				selector = selector.slice(0, selector.lastIndexOf(", "));
				lastCells = table.find(selector);
			} else {
				lastCells = table
					.children("thead")
					.children("tr")
					.not("[data-skip=true]")
					.children("th:last-child")
					.not("[data-skip=true]");
			}
			currIncrement = parseInt(lastCells.first().attr("data-vscr-padding-icrement"), 10);
			paddingValue = parseInt(lastCells.first().css(this._padding), 10);
			if (currIncrement) {
				//if the padding has currently been incremented
				//because of a vScrollbar we should remove that increment
				paddingValue -= currIncrement;
			}
			this._removeHeaderCellPadding(table);
			/* hide the table to trigger just one reflow */
			/* L.A. 30 July 2012 - Fixing bug #117293 The drop-down menu is
			closing when you try to use keyboard navigation in IE */
			/* table.css("display", "none"); */
			lastCells.css(this._padding, paddingValue + paddingIncrement);
			lastCells.first().attr("data-vscr-padding-icrement", paddingIncrement);
			/* update the margin to be exactly the oposite of the padding cell
			for the indicators that should always stay on the right regardless of the padding
			this is the case with the resizing and hiding indicators */
			lastCells
				.find("[data-nonpaddedindicator=right]")
				.css(this._rtl ? "margin-left" :
				"margin-right", -parseInt(lastCells.css(this._padding), 10) + "px");
			/* L.A. 30 July 2012 - Fixing bug #117293 The drop-down menu is
			closing when you try to use keyboard navigation in IE */
			/* table.css('display', ''); */
		},
		_removeHeaderCellPadding: function (table, removeAllPaddings) {
			var withoutLastCell = ":not(:last)";
			if (removeAllPaddings === true) {
				withoutLastCell = "";
			}
			table.find("thead > tr > th" + withoutLastCell +
				"[data-vscr-padding-icrement],thead > tr > td:not(:last)[data-vscr-padding-icrement]")
				.removeAttr("data-vscr-padding-icrement")
				.css(this._padding, "");
		},
		_removeCellPadding: function (table, tableGroup, rowElement, removeAllPaddings) {
			// M.H. 4 Sep 2013 Fix for bug #144735: Right aligned last column content hides under scrollbar
			var i, $cell, cells, withoutLastCell = ":not(:last)";
			if (removeAllPaddings === true) {
				withoutLastCell = "";
			}
			cells = table
				.find(" > " + tableGroup + " > tr:first > " + rowElement +
				withoutLastCell + "[data-vscr-padding-icrement]");
			for (i = 0; i < cells.length; i++) {
				$cell = $(cells[ i ]);
				$cell.removeAttr("data-vscr-padding-icrement");
				table
					.find(" > " + tableGroup + " > tr > " + rowElement +
					":nth-child(" + ($cell.index() + 1) + ")")
					.css(this._padding, "");
			}
		},
		_increaseLastCellVScrollbarPadding: function (table, tableGroup, rowElement, paddingIncrement) {
			var paddingValue,
				lastCells = table.find(" > " + tableGroup + " > tr > " + rowElement + ":last-child"),
				currIncrement = parseInt(lastCells.first().attr("data-vscr-padding-icrement"), 10),
				selector = "", i = 0;
			if (this._isMultiRowGrid()) {
				for ( i = 0; i < this._rlm.length; i++) {
					selector += "td[aria-describedBy ='" + this.id() + "_" +
						this._rlm[ i ][ this._maxCols - 1 ] + "'] , ";
				}
				selector = selector.slice(0, selector.lastIndexOf(", "));
				lastCells = table.find(selector);
				currIncrement = parseInt(lastCells.first().attr("data-vscr-padding-icrement"), 10);
			}

			/* M.H. 4 Sep 2013 Fix for bug #144735: Right
			aligned last column content hides under scrollbar */
			this._removeCellPadding(table, tableGroup, rowElement);
			/* A.T. 9 July - Fix for bug #144735 - right
			aligned last column content hides under scrollbar */
			paddingValue = parseInt(lastCells.first().css(this._padding), 10);
			paddingValue = paddingValue || 0;
			if (currIncrement) {
				//if the padding has currently been incremented
				//because of a vScrollbar we should remove that increment
				paddingValue -= currIncrement;
			}
			lastCells.css(this._padding, paddingValue + paddingIncrement);
			lastCells.first().attr("data-vscr-padding-icrement", paddingIncrement);
		},
		_updateHScrollbarVisibility: function () {
			var o = this.options,
				$hScrollCntnrInner = this._getHScrollContainerInner(),
				scrollerContainer = this._hscrollbar(),
				visibleScroller = scrollerContainer.is(":visible"),
				gridW = this._gridInnerWidth,
				contW = this._gridContentWidth,
				shouldInitHeights = false;
			/* M.H. 30 June 2015 Fix for bug 201916: Rows are extended vertically to
			full the grid height when row virtualization and column fixing are enabled. */
			if (!scrollerContainer.length && o.virtualizationMode === "continuous") {
				scrollerContainer = this._vhorizontalcontainer();
				visibleScroller = scrollerContainer.is(":visible");
			}
			scrollerContainer.css("display", "");
			/* if the grid is hidden */
			if (!scrollerContainer.is(":visible") && scrollerContainer.length > 0) {
				scrollerContainer.css("display", "none");
				return;
			}
			/* M.H. 20 Apr 2016 Fix for bug 217001: Resizing does not take into account
			the vertical scrollbar and some of cell text is not fully visible after resizing */
			if (o.rowVirtualization || o.virtualization) {
				gridW = this.element.parent().width();
				/* M.H. 25 May 2016 Fix for bug 219813: Horizontal scoll bar is not displayed when continuous row virtualization is enabled, ColumnFixing is enabled and a hidden column has width. */
				if ($.ig.util.isIE && this._allColumnWidthsInPixels) {
					contW = 0;
					this.element.find(">colgroup>col").each(function (i, col) {
						var w = col.width || col.style.width;
						if (w && w.indexOf("px") > 0) {
							contW += parseInt(w, 10);
						} else {
							contW = this.element.width();
							return false; /*break*/
						}
					});
				} else {
					contW = this.element.width();
				}
			}
			if (gridW < contW) {
				// M.H. 5 Jul 2013 Fix for bug #145572: When Resizing with continuous rowVirtualization no horizontal scrollbar appears when needed
				if (o.virtualizationMode === "continuous" &&
						(scrollerContainer.length === 0 ||
						$hScrollCntnrInner.length === 0)) {
					this._vhorizontalcontainer().empty();
					this._renderHorizontalScrollContainer(this._calculateContainerWidth(false));
					shouldInitHeights = true;
				} else if (o.virtualization || o.rowVirtualization) {
					// M.H. 24 Nov 2014 Fix for bug #185557: Width=100% setting is ignored for grid headers if virtualization is on.
					if ((!o.width || (o.width.indexOf && o.width.indexOf("%") > 0)) &&
						!this._allColumnWidthsInPercentage && !this._allColumnWidthsInPixels) {
						scrollerContainer.css("display", "none");
					}
				}
			} else {
				// M.H. 5 Jul 2013 Fix for bug #145572: When Resizing with continuous rowVirtualization no horizontal scrollbar appears when needed
				if (o.virtualizationMode === "continuous" &&
						scrollerContainer.length === 0 &&
						$hScrollCntnrInner.length) {
					this._vhorizontalcontainer().empty().attr("style", "");
					shouldInitHeights = true;
				}
				scrollerContainer.css("display", "none");
			}
			shouldInitHeights = shouldInitHeights || (scrollerContainer.is(":visible") !== visibleScroller);
			if (o.autoAdjustHeight && shouldInitHeights) {
				this._initializeHeights();
			}
		},
		_applyAutofitLastColInVirtGrid: function (gridContentWidth) {
			// option autofitLastColumn should be applied in all cases EXCEPT when:
			//	1.	Option rowVirtualization is enabled(column virtualization should be disabled) AND option autofitLastColumn is set to false
			//		- Grid width and all columns have width in pixels AND grid content width(sum of widths of all columns) is less then width of the grid
			//	2. 	Option rowVirtualization is enabled(column virtualization should be disabled) AND option autofitLastColumn is set to false
			//		- Grid width is in percantage AND all columns have widths set in pixels
			// NOTE: Use this function ONLY IF rowVirtualization is TRUE
			// NOTE: it is possible the function to be called when this._gridContentWidth is not set SO it can accept gridContentWidth as an argument
			var o = this.options, widthInPerc, widthInPx;
			if (!o.rowVirtualization || o.columnVirtualization || o.autofitLastColumn) {
				return true;
			}
			gridContentWidth = gridContentWidth || this._gridContentWidth;
			widthInPx = (this._gridHasWidthInPixels() &&
				gridContentWidth < parseFloat(o.width) &&
				gridContentWidth); // test 1st case
			widthInPerc = (this._allColumnWidthsInPixels && this._gridHasWidthInPercent()); // test 2nd case
			return !(widthInPx || widthInPerc);
		},
		_setGridWidthVirtGrid: function (width) {
			var widthUnfixedContainer = width, id = this.id(), cols, $col,
				scrlbWidth = this._scrollbarWidth(),
				hasFixedCols = this.hasFixedColumns(),
				widthInPerc = typeof width === "string" && width.indexOf("%") > 0,
				wWithoutScrlbr = width;
			if (hasFixedCols && !widthInPerc) {
				widthUnfixedContainer = parseInt(width, 10) - this.fixedBodyContainer().outerWidth();
			}
			/* M.H. 6 Jun 2016 Fix for bug 220190: Resizing the grid causes header appearance to break */
			wWithoutScrlbr = widthInPerc ? width : parseInt(widthUnfixedContainer, 10) - scrlbWidth;
			this.container().css("width", width);
			this.element.css("width", widthInPerc ? "100%" : wWithoutScrlbr);
			/* A.T. 22 Feb. Fix for bug #102486 */
			if (this.options.fixedHeaders) {
				this.headersTable().css("width", widthInPerc ? "100%" : widthUnfixedContainer);
			}
			this.container().find("#" + id + "_headers_v")
				.css("max-width", "")
				.css("width", widthInPerc ? "100%" : widthUnfixedContainer);
			this._vdisplaycontainer()
				.css("max-width", "")
				.css("width", widthInPerc ? "100%" : wWithoutScrlbr);
			this._virtualcontainer()
				.css("width", widthInPerc ? "100%" : widthUnfixedContainer);
			cols = this._virtualcontainer().find("> colgroup > col");
			$col = (hasFixedCols && this.fixingDirection() === "left") ? cols.eq(1) : cols.first();
			$col.attr("width", widthInPerc ? "100%" : wWithoutScrlbr);
			/* M.H. 15 Aug 2013 Fix for bug #149242: Misalignment when resizing grid with summaries and virtualization */
			this.container().find("#" + id + "_footer_container")
				.css("max-width", "")
				.css("width", widthInPerc ? "100%" : widthUnfixedContainer);
			this.container().find("#" + id + "_footers")
				.css("max-width", "")
				.css("width", widthInPerc ? "100%" : widthUnfixedContainer);
			/* M.H. 18 Aug 2014 Fix for bug #178284: When grid width is changed using the API
			container isn"t resized until subsequent grid resize when virtualization is enabled */
			this._vhorizontalcontainer().css("width", widthInPerc ? "100%" : widthUnfixedContainer);
			if (this.options.autofitLastColumn &&
				!(hasFixedCols && this.fixingDirection() === "right")) {
				this._rerenderColgroups();
				this._adjustLastColumnWidth(true);
			}
			this._gridInnerWidth = this._vdisplaycontainer().width();
			if (this._allColumnWidthsInPixels && widthInPerc) {
				if (this.options.fixedHeaders) {
					this.headersTable().css("max-width", this._gridInnerWidth);
				}
				this.element.css("max-width", this._gridInnerWidth);
				if (this.options.fixedFooters) {
					this.footersTable().css("max-width", this._gridInnerWidth);
				}
			}
			this._updateGridContentWidth();
		},
		_setGridWidth: function (width) {
			if (this.options.virtualization === true ||
					this.options.columnVirtualization === true ||
					this.options.rowVirtualization === true) {
				return this._setGridWidthVirtGrid(width);
			}
			var widthUnfixedContainer = width,
				hasFixedCols = this.hasFixedColumns(),
				widthInPerc = typeof width === "string" && width.indexOf("%") > 0;
			if (hasFixedCols && width && !widthInPerc) {
				widthUnfixedContainer = parseInt(width, 10) - this.fixedBodyContainer().outerWidth();
			} else if (widthInPerc) {
				widthUnfixedContainer = "100%";
			}
			this.container().css("width", width);
			this.element.css("width", widthUnfixedContainer);
			/* A.T. 22 Feb. Fix for bug #102486 */
			if (this.options.fixedHeaders) {
				this.headersTable().css("width", widthInPerc ? "100%" : width);
			}
			if (this.options.fixedFooters) {
				this.footersTable().css("width", widthInPerc ? "100%" : width);
			}
			/* M.H. 21 Mar 2013 Fix for bug #136999: igGrid does not resize properly. */
			if (!this._allColumnWidthsInPercentage || !widthInPerc) {
				this._gridInnerWidth = this.scrollContainer().width();
			}
			if (this.options.autofitLastColumn &&
				!(hasFixedCols && this.fixingDirection() === "right")) {
				this._rerenderColgroups();
				this._adjustLastColumnWidth(true);
			}
			/* M.H. 1 Apr 2013 Fix for bug #138302: Horizontal scrollbar doesn't appear when resizing the igGrid */
			if (this.options.width !== null || this.options.height !== null) {
				// M.H. 20 May 2013 Fix for bug #136999: igGrid does not resize properly.
				this._updateGridContentWidth();
			}
			/* this._fireInternalEvent("_optionWidthChanged"); */
		},
		_setGridContentWidth: function (gridContentWidth) {
			var o = this.options, w, $hscrlbar, $parent, oAvgRowHeight,
				fixedCols = this.hasFixedColumns(),
				fixedDirRight = this.fixingDirection() === "right",
				v = o.virtualization === true ||
					o.rowVirtualization === true ||
					o.columnVirtualization === true;
			/* M.H. 11 Jan 2016 Fix for bug 212105: Option autofitLastColumn:false is
			not working properly when rowVirtualization is enabled and total width of
			the columns is less than grid width */
			if (v === false ||
				(o.rowVirtualization && !this._applyAutofitLastColInVirtGrid(gridContentWidth))) {
				this._setGridTablesWidth(gridContentWidth);
				if (v) {
					this._avgRowHeight = this._calculateAvgRowHeight();
					if (oAvgRowHeight !== this._avgRowHeight) {
						this._trigger("avgRowHeightChanged", null,
							{
								owner: this,
								oAvgRowHeight: null,
								avgRowHeight: this._avgRowHeight
							}
						);
					}
				}
			} else {
				/* M.H. 13 Apr 2016 Fix for bug 216297: Hiding a column after unfixing it
				with fixing direction right hides the scrollbar or doesn't render it properly */
				this._checkAndSetTableWidths();
			}
			if (this.options.height !== null) {
				this._gridContentWidth = gridContentWidth || this._gridContentWidth;
				if (this._gridContentWidth) {
					/* M.H. 26 Jun 2013 Fix for bug #142942: When Paging is enabled changing
					the page size to value big enough to have a vertical scrollbar while a
					column is fixed makes the grid misaligned when you scroll the grid to the right. */
					/* M.H. 16 Sep 2015 Fix for bug 206647: Width of horizontal
					scrollbar is not properly calculated when rowVirtualization is enabled */
					/* when rowVirtualization is enabled - there is verticalScrollbar AND this._hasVerticalScrollbar is not set */
					w = ((this._hasVerticalScrollbar ||
						this.options.rowVirtualization ||
						(this.options.virtualizationMode === "continuous" &&
						this.options.virtualization)) &&
						!v &&// M.H. 25 Apr 2016 Fix for bug 215385: After showing an initially hidden column the header table's right end is not fully visible in FireFox
						!(fixedCols && fixedDirRight) && /* M.H. 8 Jun 2016 Fix for bug 220495: Setting dynamically width of the grid in percentage when fixing direction is right and there is at least one fixed column causes width of the horizontal bar to be incorrect */
						this.options.fixedHeaders ?
						this._gridContentWidth - this._scrollbarWidth() : this._gridContentWidth);
					/* L.A. 23 April 2012 Fixing bug #99024 When in grid fixedHeaders
					is false, it is not possible to resize last column */
					/* L.A. 26 April 2012 Fixing bug #91585 In IE7 columns are not correct after scroll horizontal */
					$hscrlbar = this._getHScrollContainerInner();
					if (!v && !$hscrlbar.length) {
						$parent = this.element.parent();
						this._addHorizontalScrollBar($parent);
						/* when height is changed and hscrollbar(igGrid horizontal scrollbar)is added we
						should check overflow-x - if auto/visible - then second scrollbar is shown */
						if ($parent.css("overflow-x") !== "hidden") {
							$parent.css("overflow-x", "hidden");
						}
						$hscrlbar = this._getHScrollContainerInner();
					}
					/* M.H. 3 Jun 2016 Fix for bug 220182: Resizing is not working properly when fixing direction is right and grid width is set in %(rowVirtualization should be enabled) */
					w = v ? this.element.width() || w : w;
					$hscrlbar.css("width", w);
				}
				this._updateHScrollbarVisibility();
			}
		},
		_checkAndSetTableWidths: function () {
			// calculate width of the table as sum of width of col in colgroup(if there is a columns width that is not set OR is in % width is NOT set for the table)
			// this function should be called when all colgroups(of headers/footers/data tables) are rendered
			// when table width(set in attribute style) or has width in percentage then width is not calculated for this table
			var o = this.options, w, func;
			func = function (tbl, w) {
				if (!tbl.length) {
					return;
				}
				var tblw = tbl[ 0 ].style.width; // table style width
				if (!tblw || tblw.indexOf("%") > 0) {
					return null;
				}
				if (!w) {
					w = 0;
					tbl.find(">colgroup>col").each(function (ind, col) {
						var cw = col.width || col.style.width;
						if (!cw || (cw.indexOf && cw.indexOf("%") > 0)) {
							w = 0;
							return false;
						}
						w += parseInt(cw, 10);
					});
				}
				if (w) {
					tbl[ 0 ].style.width = w + "px";
				}
				return w;
			};
			if (o.showHeader && o.fixedHeaders && o.height !== null) { // fixed headers
				w = func(this.headersTable());
			}
			func(this.element, w);
			if (o.showFooter && o.fixedFooters === true && o.height !== null) {
				func(this.footersTable(), w);
			}
		},
		_setGridTablesWidth: function (width) {
			var hasFixedHeaders = this.options.showHeader &&
				this.options.fixedHeaders === true &&
				this.options.height !== null,
				hasFixedFooters = this.options.showFooter &&
				this.options.fixedFooters === true &&
				this.options.height !== null;
			this.element.css("width", width);
			if (hasFixedHeaders) {
				this.headersTable().css("width", width);
			}
			if (hasFixedFooters) {
				this.footersTable().css("width", width);
			}
		},
		_detachHiddenColumns: function () {
			var col, i;
			for (i = 0; i < this.options.columns.length; i++) {
				col = this.options.columns[ i ];
				if (col.hidden) {
					this._detachColumn(col);
				}
			}
		},
		_rerenderColgroups: function () {
			var hasFixedHeader = this.options.height !== null &&
				this.options.fixedHeaders === true &&
				this.options.showHeader,
				hasFixedFooter = this.options.height !== null &&
				this.options.fixedFooters === true &&
				this.options.showFooter,
				initial = this.options.autofitLastColumn;

			if (hasFixedHeader) {
				this.headersTable().children("colgroup").remove();
				this._renderColgroup(this.headersTable()[ 0 ], true, false, initial);
			}
			if (hasFixedFooter) {
				this.footersTable().children("colgroup").remove();
				this._renderColgroup(this.footersTable()[ 0 ], false, true, initial);
			}
			this.element.children("colgroup").remove();
			this._renderColgroup(this.element[ 0 ], false, false, initial);
		},
		_detachColumn: function (col) {
			var position, cols, headerCells, isMultiColumnGrid = this._isMultiColumnGrid;
			col.hidden = false;
			this._visibleColumnsArray = undefined;
			cols = this._visibleColumns();
			position = $.inArray(col, cols);
			col.hidden = true;
			this._visibleColumnsArray = undefined;
			this._initializeDetachedContainers();
			headerCells = this.headersTable().children("thead")
				.children("tr").not("[data-skip=true]");
			if (isMultiColumnGrid) {
				this._hideMultiHeaderCells(this._headerCells, col.key);
				headerCells = this.headersTable().children("thead")
					.children("tr:not([data-mch-level])").not("[data-skip=true]");
				this._detachCells(
					headerCells,
					function (row) {
						return row.filter(":not([data-new-row],[data-add-row])")
							.children("th, td").not("[data-skip=true]");
					},
					position,
					this._detachedHeaderCells,
					col.key
				);
			} else {
				this._detachCells(
						headerCells,
						function (row) {
							return row.filter(":not([data-new-row],[data-add-row])")
								.children("th, td").not("[data-skip=true]");
						},
					position,
					this._detachedHeaderCells,
					col.key
				);
			}
			this._detachCells(
				this.footersTable().children("tfoot").children("tr"),
				function (row) { return row.children("td").not("[data-skip=true]"); },
				position,
				this._detachedFooterCells,
				col.key
			);
			return position;
		},
		_attachColumn: function (col) {
			var headerCells, visibleCols = this._visibleColumns(),
				position = $.inArray(col, visibleCols);
			this._initializeDetachedContainers();
			if (this._isMultiColumnGrid) {
				this._showMultiHeaderCells(col.key);
				headerCells = this.headersTable().children("thead")
					.children("tr:not([data-mch-level])").not("[data-skip=true]");
				this._attachCells(
					headerCells,
					function (row) { return row.children("th, td").not("[data-skip=true]"); },
					position,
					this._detachedHeaderCells,
					col.key
				);
				/* P.Zh. 12 April 2016 Fix for bug #207121: Hiding a sorted column
				and sorting another doesn't remove the sorting icon on the first
				one when having Multicolumn Header in igGrid */
				delete this._detachedHeaderCells[ col.key ];
			} else {
				this._attachCells(
					this.headersTable().children("thead").children("tr").not("[data-skip=true]"),
					function (row) { return row.children("th, td").not("[data-skip=true]"); },
					position,
					this._detachedHeaderCells,
					col.key
				);
			}
			this._attachCells(
				this.footersTable().children("tfoot").children("tr"),
				function (row) { return row.children("td").not("[data-skip=true]"); },
				position,
				this._detachedFooterCells,
				col.key
			);
			return position;
		},
		_initializeDetachedContainers: function () {
			if (!this._detachedContainersInitialized) {
				this._detachedHeaderCells = {};
				this._detachedFooterCells = {};
				this._detachedContainersInitialized = true;
			}
		},
		_getParentsMultiHeader: function (key) {
			var i, cols = this._oldCols, colsLength = cols.length, arr = [];
			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].key === key || cols[ i ].identifier === key) {
					arr.push(cols[ i ]);
					break;
				}
				if (cols[ i ].group) {
					if (this._getParentsMultiHeaderRecursive(key, cols[ i ].group, arr) === true) {
						arr.push(cols[ i ]);
						break;
					}
				}
			}
			return arr;
		},
		_getParentsMultiHeaderRecursive: function (key, arr, resArr) {
			var i, cols = arr, colsLength = arr.length, res = false;
			for (i = 0; i < colsLength; i++) {
				if (cols[ i ].key === key || cols[ i ].identifier === key) {
					resArr.push(cols[ i ]);
					res = true;
					break;
				}
				if (cols[ i ].group &&
					this._getParentsMultiHeaderRecursive(key, cols[ i ].group, resArr) === true) {
					resArr.push(cols[ i ]);
					res = true;
					break;
				}
			}
			return res;
		},
		_hideMultiHeaderCells: function (headerCells, key) {
			var i, currentCell, parents = this._getParentsMultiHeader(key), parent, colspan, $th,
				headersTable = this.headersTable().find("thead > tr"), $tr, position = -1, cells = [];
			for (i = 0; i < headerCells.length; i++) {
				if (this.id() + "_" + key === headerCells[ i ].attr("id")) {
					position = i;
					break;
				}
			}
			if (position === -1) {
				/* M.H. 10 May 2016 Fix for bug 218799: Setting a column as
				hidden in grid with column fixing with columns fixed and multi
				column header enabled, when unhiding columns causes column to not render properly */
				currentCell = $("#" + this.id() + "_" + key);
				if (!currentCell.length || !currentCell.parent().is("tr")) {
					return;
				}
			} else {
				currentCell = headerCells[ position ];
				this._headerCells.splice(position, 1);
			}
			$tr = currentCell.closest("tr");
			if ($tr[ 0 ].style.height === "") {
				$tr[ 0 ].style.height = $tr.outerHeight() + "px";
			}
			currentCell.css("display", "none");
			this._hiddenColumns[ key ] = currentCell;
			/* P.Zh. 12 April 2016 Fix for bug #207121: Hiding a sorted column and sorting
			another doesn't remove the sorting icon on the first one when having Multicolumn Header in igGrid */
			cells.push(currentCell);
			this._detachedHeaderCells[ key ] = cells;
			if (parents.length > 0) {
				for (i = 0; i < parents.length; i++) {
					parent = parents[ i ];
					if (parent.level === 0) {
						continue;
					}
					$th = headersTable.find("th[data-mch-id=" + parent.identifier + "]");
					/* in case of fixed column */
					if ($th.length === 0) {
						$th = this.fixedHeadersTable()
							.find("thead > tr")
							.find("th[data-mch-id=" + parent.identifier + "]");
					}
					colspan = parseInt($th.attr("colspan"), 10);
					if (colspan > 1) {
						$th.attr("colspan", --colspan);
						parent.colspan = colspan;
					} else {
						$tr = $th.closest("tr");
						if ($tr[ 0 ].style.height === "") {
							$tr[ 0 ].style.height = $tr.outerHeight() + "px";
						}
						$th.attr("ishidden", 1);
						$th.css("display", "none");
						parent.hidden = true;
						parent.colspan = 0;
					}
				}
			}
		},
		_detachCells: function (rows, cellSelectorFunction, position, container, key) {
			var detachedCells = [], i, cells, currentCell;
			if (rows.length > 0) {
				for (i = 0; i < rows.length; i++) {
					cells = cellSelectorFunction(rows.eq(i));
					currentCell = cells.eq(position);
					if (cells.length === 1) {
						currentCell.after(
							$("<" + currentCell[ 0 ].tagName + "></" + currentCell[ 0 ].tagName + ">")
								.attr("data-hiddenreplacement", "true")
								.css("height", currentCell.height())
								.attr("class", currentCell.attr("class"))
						);
					}
					detachedCells.push(currentCell.detach());
				}
				container[ key ] = detachedCells;
			}
		},
		_showMultiHeaderCells: function (key) {
			var i, currentCell = this._hiddenColumns[ key ],
				parents = this._getParentsMultiHeader(key),
				parent, colspan, $th, currentOrder, order,
				headerCellsLength = this._headerCells.length,
				headersTable = this.headersTable().find("thead > tr"), isHidden;
			if (currentCell === null || currentCell === undefined) {
				return;
			}
			currentOrder = currentCell.data("data-mch-order");
			for (i = 0; i < headerCellsLength; i++) {
				order = this._headerCells[ i ].data("data-mch-order");
				if (currentOrder < order) {
					this._headerCells.splice(i, 0, currentCell);
					break;
				}
			}
			if (i === headerCellsLength) {
				this._headerCells.push(currentCell);
			}
			currentCell.css("display", "");
			currentCell.removeAttr("ishidden");
			if (parents.length > 0) {
				for (i = 0; i < parents.length; i++) {
					parent = parents[ i ];
					if (parent.level === 0) {
						continue;
					}
					$th = headersTable.find("th[data-mch-id=" + parent.identifier + "]");
					/* in case of fixed column */
					if ($th.length === 0) {
						$th = this.fixedHeadersTable()
							.find("thead > tr")
							.find("th[data-mch-id=" + parent.identifier + "]");
					}
					isHidden = $th.attr("ishidden") === "1";
					colspan = parseInt($th.attr("colspan"), 10);
					if (!isHidden) {
						$th.attr("colspan", ++colspan);
						parent.colspan = colspan;
					} else {
						$th.removeAttr("ishidden");
						$th.css("display", "");
						parent.hidden = false;
						parent.colspan = 1;
					}
				}
			}
		},
		_attachCells: function (rows, cellSelectorFunction, position, container, key) {
			var detachedCells, i, cells, replacementCell, storedKey;
			if (rows.length > 0) {
				if (this.options.virtualization === true || this.options.columnVirtualization === true) {
					// M.H. 5 April 2012 Fix for bug #104627
					detachedCells = container[ key ];
					/*if we are using virtualization the column that needs to be attached
					may not be in the list of detached columns as the headers are reused
					and because of that any column from the list can be attached */
					/* M.H. 5 April 2012 Fix for bug #104627 */
					if (detachedCells === null || detachedCells === undefined) {
						for (storedKey in container) {
							if (container.hasOwnProperty(storedKey)) {
								detachedCells = container[ storedKey ];
								delete container[ storedKey ];
								break;
							}
						}
					} else {
						// M.H. 24 Jun 2013 Fix for bug #145301: Sorting on previously hidden column causes an error to be thrown when virtualization is enabled.
						delete container[ key ];
					}
				} else {
					detachedCells = container[ key ];
					delete container[ key ];
				}
				/* M.H. 23 Jan 2012 Fix for bug #91738 */
				if (detachedCells === undefined || detachedCells === null) {
					return;
				}
				for (i = 0; i < rows.length; i++) {
					cells = cellSelectorFunction(rows.eq(i)).not("[data-hiddenreplacement=true]");
					if (position === 0) {
						if (cells.length === 0) {
							replacementCell = rows.eq(i).find("[data-hiddenreplacement=true]");
							replacementCell.after(detachedCells[ i ]);
							replacementCell.remove();
						} else {
							//append before the second element which is now at position 0
							cells.eq(0).before(detachedCells[ i ]);
						}
					} else {
						//append after the element before the current
						cells.eq(position - 1).after(detachedCells[ i ]);
					}
				}
			}
		},
		_cleanupTBody: function () {
			// emptying the TBODY with removeChild will not execute any of the destroy
			// functions of underlying grids and their features leading to memory leaks
			// and a variety of other issues - commenting for future revision.
			/*var tbody = this.element.children('tbody');
			tbody.empty();
			if ($.ui.igGrid.speedupDOMCleanup === false) {
				tbody.empty();
			} else {
				if (tbody.children().length > 0) {
					this.element[0].removeChild(tbody[0]);
					//M.K. 28 May 2015: Move TFOOT rendering after the TBODY (in order to support ARIA).
					tbody = $('<tbody role="rowgroup"></tbody>');
					if (this.element.children('tfoot').length > 0) {
						tbody.insertBefore(this.element.children('tfoot'));
					} else {
						tbody.appendTo(this.element);
					}
					tbody.addClass(this.css.baseContentClass)
						.addClass(this.css.gridTableBodyClass)
						.addClass(this.css.recordClass);
				}
			}*/
			/* M.H. 11 Mar 2016 Fix for bug 215748: Grid with ColumFixing,
			virtualization:continuous and Filtering throws the script error
			"Uncaught TypeError: Cannot read property 'offsetHeight' of undefined"
			when filtering results is zero. */
			this._fireInternalEvent("_cleanupTBody");
			return this.element.children("tbody").empty();
		},
		_captureInitiallyHiddenColumns: function () {
			//capture the columns that are hidden initially and mark them as visible
			//the grid should render as all columns are visible and adjust later if there are hidden ones
			this._initialHiddenColumns = $.grep(this.options.columns, function (column) {
				var hidden = column.hidden;
				column.hidden = false;
				return hidden;
			});
		},
		getUnboundValues: function (key) {
			/*
			Gets unbound values for the specified column key. If key is not specified returns all unboundvalues
			paramType="string" column key
			returnType="object" unbound values
			*/
			var res;
			if (key === undefined || key === null) {
				return this._unboundValues;
			}
			res = this._unboundValues[ key ];
			/* M.H. 3 Sep 2012 Fix for bug #120188 */
			if (res === undefined || res === null) {
				if (this.getUnboundColumnByKey(key) !== null) {
					res = [];
				} else {
					res = null;
				}
			}
			return res;
		},
		setUnboundValues: function (key, values, removeOldValues) {
			/*
				Sets unbound values for the unbound column with the specified key. If removeOldValues is true then values(if any) for the unbound columns are re-set with the new values
				paramType="string" key of the unbound column
				paramType="array" array of values to be set on unbound values
				paramType="boolean" if true removes current unbound values(if any) for the specified column and apply the new ones specified in parameter values. Otherwise merge current values with the specified in parameter values
			*/
			/* M.H. 9 Oct 2012 Fix for bug #123732 */
			var i, column = this.getUnboundColumnByKey(key), uVals, data, len, reRenderData = false;
			if (column && !column.formula) {
				uVals = column.unboundValues;
				if (removeOldValues) {
					column.unboundValues = values;
					data = this.dataSource.data();
					len = data.length;
					for (i = 0; i < len; i++) {
						if (data[ i ][ key ] !== null && data[ i ][ key ] !== undefined) {
							reRenderData = true;
							delete data[ i ][ key ];
						}
					}
					if (reRenderData) {
						this._renderData();
					}
				} else {
					len = values.length;
					if (uVals && uVals.length > len) {
						for (i = 0; i < len; i++) {
							column.unboundValues[ i ] = values[ i ];
						}
					} else {
						column.unboundValues = values;
					}
				}
			}
			/* M.H. 26 May 2014 Fix for bug #172283: When the grid is bound to
			remote data source, sorting the grid duplicates the unbound vaues */
			if (!this._hasInitialUnboundValues) {
				this._hasInitialUnboundValues = {};
			}
			this._hasInitialUnboundValues[ key ] = true;
			this._renderUnboundValues(values, key);
		},
		setUnboundValueByPK: function (col, rowId, val, notToRender) {
			/*
				Sets unbound value for the unbound cell by the specified column key and row primary key.
				paramType="string" key of the unbound column
				paramType="string" primary key value of the row
				paramType="object" value to be set on unbound cell
				paramType="boolean" if false will re-render the row
			*/
			var data = this.dataSource.data(), pkCol, rec, tr, f,
				pk = this.options.primaryKey, colKey, pkVal;
			if ($.type(col) === "string") {
				colKey = col;
				col = this.columnByKey(colKey);
			} else {
				colKey = col.key;
			}
			if (!col || !col.unbound) {
				return false;
			}
			/* if pk is not defined */
			if (pk === null) {
				pkVal = parseInt(rowId, 10);
				rec = data[ pkVal ];
			} else {
				pkCol = this.columnByKey(this.options.primaryKey);
				if (pkCol) {
					pkVal = rowId;
					if (pkCol.dataType === "number" || pkCol.dataType === "numeric") {
						pkVal = parseInt(rowId, 10);
						rec = this.dataSource.findRecordByKey(rowId);
					} else {
						rec = this.dataSource.findRecordByKey(rowId);
					}
				} else {
					return false;
				}
			}
			if (!rec) {
				return false;
			}
			if (val === undefined) {
				if (col.formula) {
					f = this._getUnboundColumnFormula(col);
					if (f) {
						val = f.apply(col, [ rec, this.element ]);
					}
				}
			}
			/* update data record */
			if (this.dataSource && this.dataSource.schema) {
				rec[ colKey ] = this.dataSource.schema()._convertType(col.type, val, pkVal, colKey);
			} else {
				rec[ colKey ] = val;
			}
			/* update UI as re-render row */
			/* if there are fixed columns  - re-renders only those (fixed/unfixed)part
			of the DOM representation of the row where the unbound column is */
			if (!notToRender) {
				tr = this.rowById(rowId, col.isFixed);
				if (tr.length) {
					this._renderRow(rec, tr[ 0 ]);
				}
			}
			return rec;
		},
		_getUnboundColumnFormula: function (col) {
			// col could be column key or column object
			// returns for the specified column option formula - if there isn't such column or formula option is not set - returns null
			if (!col) {
				return null;
			}
			var f = null, colType = typeof col;
			/* col is column key */
			if (colType === "string") {
				col = this.getUnboundColumnByKey(col);
				if (!col) {
					return null;
				}
				colType = typeof col;
			}
			if (!col.formula || colType !== "object") {
				return null;
			}
			f = col.formula;
			if (typeof f === "function") {
				return f;
			} else if (window[ f ] && typeof window[ f ] === "function") {
				return window[ f ];
			}
			return null;
		},
		/* M.H. 9 Oct 2012 Fix for bug #123732 */
		_renderUnboundValues: function (values, key, notToRender) {
			/*
				Sets unbound values for the unbound column with the specified key.
				paramType="number" array of values
				paramType="string" key of the unbound column
				paramType="bool" optional - when set to true only set values into the datasource without render them in the grid
			*/
			var i, data = this.dataSource.data(), dataLength = data.length, valuesLength = values.length,
				isToConvert = false, type = "string", pk = this.options.primaryKey, col, schema, pkVal;
			if (valuesLength === 0) {
				return;
			}
			/* M.H. 20 Sep 2012 Fix for bug #121830 */
			col = this.getUnboundColumnByKey(key);
			if (col === null) {
				return;
			}
			if (this.dataSource && this.dataSource.schema) {
				if (col.dataType) {
					type = col.dataType;
					isToConvert = true;
					schema = this.dataSource.schema();
				}
			}
			if (pk === undefined) {
				pk = null;
			}
			/* M.H. 4 Sep 2012 Fix for bug #120302 */
			for (i = 0; i < valuesLength; i++) {
				if (data[ i ] === undefined || data[ i ] === null) {
					break;
				}
				if (isToConvert) {
					// M.H. 13 Sep 2012 Fix for bug #120867
					if (pk === null) {
						pkVal = i;
					} else {
						pkVal = data[ i ][ pk ];
					}
					values[ i ] = schema._convertType(type, values[ i ], pkVal, key);
				}
				data[ i ][ key ] = values[ i ];
				/* M.H. 10 Sep 2012 Fix for bug #120696 */
				this._addUnboundColumnValue(key, values[ i ], i);
			}
			/* M.H. 9 Nov 2012 Fix for bug #126903 */
			if ((type === "bool" || type === "boolean") && valuesLength < dataLength) {
				for (i = valuesLength; i < dataLength; i++) {
					data[ i ][ key ] = schema._convertType(type, null, pkVal, key);
				}
			}
			/* M.H. 10 Sep 2012 Fix for bug #120720 */
			if (this.options.virtualization === true ||
				this.options.rowVirtualization === true &&
				this._persistVirtualScrollTop) {
				this._scrollTo(0);
			}
			if (notToRender !== true) {
				this._renderColumnData(key, this.dataSource.dataView());
			}
		},
		getUnboundColumnByKey: function (key) {
			/*
			Returns an unbound column with the specified key. If not found returns null
			paramType="string" a column key
			returnType="object" a column definition
			*/
			if (this._unboundColumns === undefined ||
					this._unboundColumns === null ||
					this._unboundColumns.length === 0 ||
					key === null ||
					key === undefined) {
				return null;
			}
			var column = null;
			$.each(this._unboundColumns, function (ind, col) {
				if (col.key === key) {
					column = col;
					return false;
				}
			});
			return column;
		},
		_renderColumnData: function (key, values) {
			/*
			Render for the column with the specified key the array of values
			paramType="string" column key
			paramType="array" array of objects
			*/
			if (values.length === 0) {
				return;
			}
			var visibleColumns = this._visibleColumns(),
				grid = this,
				tdIndex,
				index,
				tds,
				column,
				ds,
				isToConvertToString = false,
				hasTemplate,
				temp;
			/* M.H. 27 Aug 2012 Fix for bug #119756 */
			index = this.getVisibleIndexByKey(key);
			column = visibleColumns[ index ];
			hasTemplate = column.template && column.template.length > 0;
			/* M.H. 5 Sep 2012 Fix for bug #120516 */
			if (column === undefined || column === null) {
				return;
			}
			isToConvertToString = (column.dataType === "bool");
			if (hasTemplate) {
				ds = this.dataSource.dataView();
			}
			/* M.H. 5 Sep 2012 Fix for bug #120501 */
			/* M.H. 7 Sep 2012 Fix for bug #120615 */
			tdIndex = index + this.headersTable().find(">thead>tr:eq(0)>th[data-skip=true]").length;
			tds = this.element.find(">tbody> tr > td:not([data-skip]):nth-child(" + (tdIndex + 1) + ")");
			$.each(values, function (ind, val) {
				var innerHtml;
				/* M.H. 4 Sep 2012 Fix for bug #120306 */
				if (val[ key ] === undefined) {
					return true;
				}
				if (hasTemplate) {
					// M.H. 6 Sep 2012 Fix for bug #119627
					temp = grid._renderTemplatedCell(ds[ ind ], column);
					if (temp.indexOf("<td") === 0) {
						innerHtml = $(temp).html();
					} else {
						innerHtml = temp;
					}
				} else {
					innerHtml = grid._renderCell(val[ key ], column, val);
					/* M.H. 1 Nov 2012 Fix for bug #126258 */
					if (isToConvertToString === true) {
						innerHtml = innerHtml.toString();
					}
				}
				if (!tds[ ind ]) {
					// break;
					return false;
				}
				$(tds[ ind ]).html(innerHtml);
			});
		},
		_renderData: function (success, errmsg, response) {
			// L.A. 22 March 2012 Bug 102502 - The dataBound event is fired before the grid's dataSource has any data (regardless if local or remote)
			// M.H. 26 Mar 2013 Fix for bug #126556: DataBound event is fired twice after GroupBy when explicitly invoking "dataBind" method
			if (success !== undefined || !this._isDataBoundCalled) {
				this._trigger(this.events.dataBound, null, { owner: this, dataSource: this.dataSource });
				this._isDataBoundCalled = false;
			}
			var gridElement = this.element, div,
				self = this,
				noCancel = true,
				sum = 0,
				cols,
				colsToUnbound,
				colsLength,
				data,
				key,
				j, i, f, w,
				diff = 0,
				isTable = false,
				noCancelError = true,
				isRemoteDS = (this._inferOpType() === "remote"),
				ucFormulaFunction,
				tbody,//gridElement.find('tbody')
				newW,
				totalWidth,
				isToRefreshUI = false,
				jsrnd = String(this.options.templatingEngine).toLowerCase() === "jsrender",
				displayCont;
			/* M.H. 1 Oct 2012 Fix for bug #123198 */
			/* M.H. 6 Nov 2012 Fix for bug #120553 */
			if ((this.options.requiresDataBinding === true ||
				this._isToSetUnboundColumns === true ||
				isRemoteDS) && this._hasUnboundColumns) {
				this._isToSetUnboundColumns = false;
				/* M.H. 22 Jan 2013 Fix for bug #130586 */
				if (this._hasInitialUnboundValues === null || this._hasInitialUnboundValues === undefined) {
					this._hasInitialUnboundValues = {};
				}
				/* M.H. 7 Sep 2012 Fix for bug #120553 */
				/* M.H. 12 Sep 2012 Fix for bug #121028 */
				/* if (this.options.requiresDataBinding === true || this._inferOpType() === "remote") { */
				/* M.H. 12 Sep 2012 Fix for bug #121019 */
				cols = this.options.columns;
				colsLength = cols.length;
				data = this.dataSource.data();
				ucFormulaFunction = function (data, f, col) {
					$.each(data, function (ind, val) {
						/* M.H. 25 Apr 2014 Fix for bug #170559: Context of unbound
						formula function is the Window, as opposed to the Column object */
						val[ key ] = f.apply(col, [ val, gridElement ]);
						/* M.H. 10 Sep 2012 Fix for bug #120696 */
						self._addUnboundColumnValue(key, val[ key ], ind);
					});
				};
				colsToUnbound = [];
				/* M.H. 6 Nov 2012 Fix for bug #120553 */
				if (isRemoteDS) {
					this._unboundValues = {};
				}
				for (i = 0; i < colsLength; i++) {
					if (cols[ i ].unbound === true) {
						key = cols[ i ].key;
						/* M.H. 22 Jan 2013 Fix for bug #130586 */
						if (cols[ i ].unboundValues !== null &&
							cols[ i ].unboundValues !== undefined &&
							$.type(cols[ i ].unboundValues) === "array") {
							// M.H. 23 Jan 2013 Fix for bug #130586
							if (this._rebindUnboundColumns === true ||
									(cols[ i ].unboundValues.length > 0 &&
									this._hasInitialUnboundValues[ key ] === undefined)) {
								this._hasInitialUnboundValues[ key ] = true;
								this._renderUnboundValues(cols[ i ].unboundValues, key, true);
							}
						} else if (cols[ i ].formula !== null && cols[ i ].formula !== undefined) {
							f = this._getUnboundColumnFormula(cols[ i ]);
							if (f) {
								/* M.H. 25 Apr 2014 Fix for bug #170559: Context of unbound
								formula function is the Window, as opposed to the Column object */
								ucFormulaFunction(data, f, cols[ i ]);
							}
						} else if (this._unboundValues[ key ] && this._unboundValues[ key ].length > 0) {
							colsToUnbound.push(key);
						}
					}
				}
				/* M.H. 16 Apr 2014 Fix for bug #169743: Unbound values are duplicated
				for columns with empty cells after data binding and sorting any column */
				this._rebindUnboundColumns = false;
				if (this.options.localSchemaTransform === true && colsToUnbound.length > 0 && data.length > 0) {
					for (i = 0; i < colsToUnbound.length; i++) {
						key = colsToUnbound[ i ];
						if (data[ 0 ][ key ] === undefined) {
							this._renderUnboundValues(this._unboundValues[ key ], key);
						}
					}
				}
				if (this.options.mergeUnboundColumns === false) {
					this._mergeUnboundValues();
				}
			}
			if (success === false) {
				// check if there is an event requestError defined
				noCancelError = this._trigger(this.events.requestError, null, {
					owner: this,
					message: errmsg,
					response: response
				});
				/* if the handler returns false or doesn't return anything, the error
				will be propagated to teh grid and an Error object will be returned */
				if (noCancelError) {
					throw new Error(errmsg);
				}
			}
			if (this._cancelRendering === true) {
				this._cancelRendering = false;
				return;
			}
			this.element.trigger("iggriduisoftdirty", { owner: this });
			/* K.D. November 21st, 2012 Bug #127250 The auto-generated
			columns are not generated when the 'dataRendering' */
			/* event is fired and autoGenerateColumns = TRUE */
			/* Making the columns collection as well as the features available before dataRendering starts */
			if (!this._initialized) {
				// auto generate the columns collection, if options.autogenerateColumns is true
				if (this.options.autoGenerateColumns === true) {
					this._generateColumns();
					/* M.H. 3 Sep 2013 Fix for bug #146575: Horizontal bar is missing
					when autoGenerateColumns = true and columnVirtualization = true */
					if ((this.options.virtualization === true || this.options.columnVirtualization === true) &&
						this.options.width) {
						totalWidth = this._calculateContainerWidth(false);
						if (totalWidth > parseInt(this.options.width, 10) &&
								$("#" + this.id() + "_horizontalScrollContainer").children().length === 0) {
							this._renderHorizontalScrollContainer(totalWidth);
						}
					}
					/* reinitialize all features */
					for (i = 0; i < this.options.features.length; i++) {
						this._initFeature(this.options.features[ i ], this._dataOptions);
					}
				} else {
					cols = this.options.columns;
					for (i = 0; i < cols.length; i++) {
						if (!cols[ i ].dataType && this.dataSource.data().length > 0) {
							cols[ i ].dataType = $.ig.getColType(this.dataSource.data()[ 0 ][ cols[ i ].key ]);
						} else if (!cols[ i ].dataType && this.dataSource.data().length === 0) {
							cols[ i ].dataType = "string";
						}
					}
				}
			}
			tbody = gridElement.children("tbody");
			noCancel = this._trigger(this.events.dataRendering, null, { owner: this, tbody: tbody });
			if (noCancel) {
				// show loading indicator
				//this.options.dataSource = this.dataSource.dataView();
				// generate and render markup on the client
				// reset virtualization buffer
				/* jQuery's empty() will try to find all handlers, etc. which is very slow. On the other hand if we don't use a similar approach
					and just remove the tbody, there will be memory leaks (inherent in Firefox and IE by design).
					tbody.innerHTML="" won't work for IE ! since it's read only for many types of DOM elements, including TBODY !
				*/
				/* if (this.options.jQueryTemplating === true) { */
				if (!(this.options.dataSource &&
					this.options.dataSource.tagName &&
					$(this.options.dataSource).is("table"))) {
					tbody = this._cleanupTBody();
				} else {
					isTable = true;
				}
				this._resetVirtualDom();
				if (!this._initialized) {
					// auto generate the columns collection, if options.autogenerateColumns is true
					if (this.options.autoGenerateColumns === true) {
						//A.Y. bug 98100. When the grid's columns are autogenerated we need to first
						//receive the data, generate the columns, init features(hiding sets the hidden option there)
						//and then capture the initially hidden columns
						this._captureInitiallyHiddenColumns();
						/* M.H. 13 Aug 2012 Fix for bug #118837 - re-initialize _visibleColumnsArray */
						this._visibleColumnsArray = undefined;
						/* A.Y. Bug 98593. We need to calculate the virtual column counters after the columns are generated. */
						this._updateVirtColCounters();
					}
					if (isTable) {
						// now clean up the TABLE
						tbody.empty();
					}
					/* A.T. 20 Dec. 2011 - fix for bug #98209 */
					/* M.H. 10 May 2012 Fix for bug #108221 - renderHeader should be called only 1 time */
					if (this._renderHeaderCalled !== true) {
						this._renderHeader();
						this._renderCaption();
						/* L.A. 20 June 2012 - Fixed bug #113607 When the grid has defined width less than the sum of its columns"
							widths and showHeader = FALSE, the grid is rendered without a horizontal scrollbar
						*/
					} else {
						if (this.options.width !== null && this.options.height === null) {
							this.scrollContainer().css("overflow-y", "hidden").css("overflow-x", "auto");
						}
					}
					/* M.H. 15 July 2015 Fix for bug 202621: Columns set with 0% width are not hidden while loading data */
					/* M.H. 24 Sep 2015 Fix for bug 207137: igPivotGrid resizing is blinking when height is not set */
					if (this._rmvClgrpOnInitRenderData &&
						!this.element.find("colgroup[data-cols-injected]").length) {
						this.element.find("colgroup").remove();
					}
					/* A.T. 21 Feb 2012 - Fix for #102322 - when we destroy the grid, we don"t remove the whole colgroup */
					if (this.element.find("colgroup").length === 0 ||
						this.element.find("colgroup").children().length === 0) {
						this._renderColgroup(this.element[ 0 ], false, false, this.options.autofitLastColumn);
					}
					this._renderFooter();
					div = this.scrollContainer();
					if (!div.length) {
						div = this._vdisplaycontainer();
					}
					/* M.H. 11 June 2015 Fix for bug 201082: When row virtualizationis enabled and
					cell is focused pressing TAB key to move the focus the cells and headers get misaligned. */
					/* in case of TABbing through data cells */
					div.data("containerName", "dataContainer")
						.bind("scroll.syncScrollContainers", $.proxy(this._onScrollContainer, this));
					this._registerScrllCntnrToSync(div);
					if ((this.options.virtualization === true || this.options.columnVirtualization === true) &&
						this._allColumnWidthsInPixels && this._gridHasWidthInPercent) {
						w = 0;
						this.element.find(">colgroup>col").each(function (ind, col) {
							var cw = col.width || col.style.width;
							if (!cw || (cw.indexOf && cw.indexOf("%") > 0)) {
								w = 0;
								return false;
							}
							w += parseInt(cw, 10);
						});
						if (w) {
							if (this.options.fixedHeaders) {
								this.headersTable().css("max-width", w);
							}
							this.element.css("max-width", w);
							if (this.options.fixedFooters) {
								this.footersTable().css("max-width", w);
							}
						}
					}
					/* used by ColumnFixing(when there are initially fixed columns) -
					this event is triggered when grid DOM is rendered BEFORE rendering <TR>s */
					this._trigger("_gridContainersRendered", null, { owner: this, tbody: tbody });
					if (this.options.virtualization && this.options.virtualizationMode === "continuous") {
						this.element.bind("iggridvirtualrecordsrender.setFocusElement", function () {
							if (_aNull(self._focusDataRowIndex)) {
								return;
							}
							var $tbody = self.container().find("#" + self.id() + " > tbody"),
								fe = self._focusDataRowIndex;
							if (fe === 0) {
									$tbody.find("> tr[data-row-idx=" + fe + "]").focus();
								} else {
									$tbody.find("> tr[data-row-idx=" + fe + "] > td:visible:last").focus();
							}
							self._focusDataRowIndex = null;
						});
					}
				}
				this._trigger("dataEmpty", null, { owner: this, tbody: tbody });
				if (this.options.autoGenerateColumns === false &&
					((this.options.columns && this.options.columns.length === 0) ||
					!this.options.columns)) {
					throw new Error($.ig.Grid.locale.noColumnsButAutoGenerateTrue);
				}
				this._tmplWrappers = jsrnd ? $.render : {};
				this._setTemplateDefinition(jsrnd);
				/* A.Y. skip rendering if the grid has columns that have to
				be hidden the data will be rendered after the hiding */
				if (this._initialHiddenColumns === undefined || this._initialHiddenColumns.length === 0) {
					/* A.T. 12 Feb - Fix for bug #65676 - Grid autogenerates columns
					when the columns are not defined and autoGenerateColumns = false */
					if (this.options.columns.length > 0) {
						if (this.options.virtualization === true ||
							this.options.rowVirtualization === true ||
							this.options.columnVirtualization === true) {
							if (!this._persistVirtualScrollTop) {
								this._scrollContainer()[ 0 ].scrollTop = 0;
							}
							this._renderVirtualRecords();
						} else {
							this._renderRecords();
						}
						if (this._initialized) {
							//this case is happending when we change the page in the paging feature
							this._adjustLastColumnWidth(false);
							if (this.options.width !== null && this.options.height !== null) {
								//A.Y. Bug 99817. In some browsers after _renderData is called and the tbody
								//is cleared and records rerendered the scrollLeft of the header and footer may become incorrect
								this._synchronizeHScroll();
							}
						} else {
							/* A.Y. Bug 97395. If we have height we need to initializeHeights before the
							call to _adjustLastColumnWidth as in some cases the grid incorrectly determines
							that there should be no vertical scrollbar when there should be */
							if (this.options.height !== null && this.options.autoAdjustHeight) {
								this._initializeHeights();
							}
							this._adjustLastColumnWidth(true);
						}
					}
				}
				/* adjust scrolling grid height */
				if (!this._initialized && this.options.autoAdjustHeight) {
					this._initializeHeights();
				}
				if (this._initialHiddenColumns !== undefined && this._initialHiddenColumns.length > 0) {
					this._setHiddenColumns(this._initialHiddenColumns, true, true);
					/* M.H. 18 July 2012 Fix for bug #113972 */
					if (this._initialHiddenCells !== undefined && this._initialHiddenCells.length > 0) {
						$.each(this._initialHiddenCells, function (index, cell) {
							cell.css("display", "");
						});
					}
					/* M.H. 15 Sep 2012 Fix for bug #105514 */
					$.each(this._initialHiddenColumns, function (index, column) {
						column._initiallyHidden = true;
					});
					this._initialHiddenCells = undefined;
					this._initialHiddenColumns = undefined;
					/* M.H. 15 Oct 2013 Fix for bug #154942: When Summaries are enabled
					and there is initially hidden column the grid does not have the
					correct height and the headers are misaligned in Firefox. */
					/* M.H. 15 Aug 2013 Fix for bug #149165: Hidden columns do
					not affect visible columns' width in Firefox */
					/* In FireFox the grid should be "re-rendered" - e.g. hidden/shown so the
					columns to be with proper width and hidden column to not take additional space */
					/* M.H. 22 Oct 2013 Fix for bug #155548: When the grid does not have
					width and a column is hidden through columnSettings the grid is misaligned in firefox */
					if ($.ig.util.isFF) {
						// this._refreshUI();
						isToRefreshUI = true;
					}
				}
				/* M.H. 27 April 2012 Fix for bug #108522 - it should be called datarendered after SetHidden columns is called */
				this._registerAdditionalEvents();
				this._updateVirtualScrollContainer();
				/* M.H. 3 Jul 2013 Fix for bug #144681: Scrollbar misbehaves when there’s a small number of records. */
				this._fireInternalEvent("_dataRendered");
				if (!this._initialized) {
					this.autoSizeColumns();
				}
				this._trigger(this.events.dataRendered, null, { owner: this });
				if (this._loadingIndicator === undefined) {
					this._initLoadingIndicator();
				}
				this._loadingIndicator.hide();
			}
			if (this._fixScrollY) {
				this.scrollContainer().css("overflow-y", this.rows().length > 1 ? "hidden" : "visible");
			}
			/* A.T. 2 March 2012 - fix for bug #99081 */
			/* the grid doesn't have a width, but such has been set on the container, which means columns have widths */
			/* M.H. 23 March 2012 Fix for bug #105213 */
			if (this.options.width === null) {
				this._updateContainersWidthOnGridWidthNull();
			}
			/* scenarios where the first load is sending JSON, but the
			second time we are actually making an AJAX request (MVC for example) */
			if (this.options.dataSourceUrl !== null && this._firstBind) {
				this.options.dataSource = this.options.dataSourceUrl;
				this.dataSource.settings.dataSource = this.options.dataSourceUrl;
				this.dataSource.settings.type = "remoteUrl";
				this.dataSource._runtimeType = this.dataSource.analyzeDataSource();
				this._firstBind = false;
				/* A.T. 26 July 2011 */
				/*  M.H. 2 Oct 2013 Fix for bug #153207: Javascript error: There was an error
				parsing/evaluating the JSON string: Invalid character occurs when RollBack is performed */
				/* Check also bug #139006: dataSourceType should be set explicitly when invoke dataBind with remote operations */
				/* M.H. 9 Oct 2013 Fix for bug #154309: Child remote operations don't work on first load */
				/* on hierarchical grid when we have paging and load on demand is false then
				paging is not properly shown for child layouts this is done because dataSourceType
				is set to json and this option is common for the whole grid and setting it to
				remoteUrl will affect parsing dataSource and will ignore totalRecordsCount */
				/* this.options.dataSourceType = "remoteUrl"; */
			}
			if (!this._initialized) {
				this._trigger(this.events.rendered, null, { owner: this });
				/* is this grid an hierarchical container or not */
				this._hc = this.container().find(".ui-iggrid-expandheadercell").length > 0;
				this._initialized = true;
				/* M.H. 11 Feb 2013 Fix for bug #131558 */
				if (document.documentMode === 7 || $.ig.util.isIE7) {
					$.each($("#" + this.id() + "_container colgroup col"), function () {
						var $col = $(this), styleWidth = parseInt(this.style.width, 10), width = $col.outerWidth();
						if (styleWidth && styleWidth < width) {
							$col.width(2 * styleWidth - width);
						}
					});
				}
			}
			this._fireInternalEvent("_gridRendered", this.container());
			if (this._isMultiRowGrid() && this.scrollContainer() && this.scrollContainer().height() === 0) {
				throw new Error($.ig.Grid.locale.scrollableGridAreaNotVisible);
			}
			/* M.H. 22 Oct 2013 Fix for bug #155548: When the grid does not have width and
			a column is hidden through columnSettings the grid is misaligned in firefox */
			if (isToRefreshUI) {
				this._refreshUI();
			}
		},
		_updateContainersWidthOnGridWidthNull: function () {
			var $cntnr = this.container(), sum = 0, j, cols, diff, displayCont,
				scw = this._scrollbarWidth();
			if (this.options.width === null && $cntnr[ 0 ] && $cntnr[ 0 ].style.width) {
				cols = this.element.find("colgroup > col");
				if (cols.length) {
					for (j = 0; j < cols.length; j++) {
						// A.T. 30 March Fix for bug #105039
						sum += parseInt(cols[ j ].style.width, 10) || 0;
					}

				} else {
					sum = parseInt($cntnr[ 0 ].style.width, 10) - scw;
				}
				/* S.S. 10 May 2012 - in virtual grids there are more containers to be resized */
				displayCont = this._vdisplaycontainer();
				if (displayCont.length > 0) {
					/* M.H. 12 Mar 2016 Fix for bug 215729: When there's no width set and
								renderExpansionIndicatorColumn is true after adding multiple child rows the
								last column stops being visible */
					/* A.T. 11 April - Fix performance issue - doing something.width()
					is a very expensive operation (because of what jQuery does under the hood) */
					$cntnr.css("width", sum);
					/*displayCont.css("width", sum - this._scrollbarWidth()).css("max-width", sum - this._scrollbarWidth()); */
					/*resize header and footer due to cont virtualization*/
					$cntnr.find("#" + this.id() + "_headers_v").css("width", sum + scw);
					$cntnr.css("width", sum + scw);
					displayCont
						.css("width", sum)
						.css("max-width", sum);
					this._fixedfooters().css("width", sum + scw);
					this._virtualcontainer().find(">colgroup>col:first").attr("width", sum);
				} else {
					$cntnr.css("width", sum += this.hasVerticalScrollbar() ? scw : 0);
				}
			}
		},
		/* M.H. 15 Aug 2013 Fix for bug #149344: When the grid has multi-column headers,
		the columns do not have widths and the height is defined hiding a column misaligns the grid. */
		_refreshUI: function () {
			var self = this;
			/* M.H. 15 Aug 2013 Fix for bug #149165: Hidden columns
			do not affect visible columns' width in Firefox */
			/* In FireFox(versions 22, 23) the grid should be "re-rendered" -
			e.g. hiding/showing container. In cases like bug 149615 and 149242 */
			self.container().hide();
			setTimeout(function () {
				self.container().show();
			}, 0);
		},
		_updateVirtualScrollContainer: function () {
			// update virtual scrollbar visibility(if there is no need of scroll e.g. all rows are visible in display container then it should not be scrollable)
			// M.H. 19 Jun 2013 Fix for bug #144681: Scrollbar misbehaves when there’s a small number of records.
			var h, e = this.element;
			this._totalRowCount = this._getTotalRowCount();
			if ((this.options.virtualization || this.options.rowVirtualization) &&
					this._totalRowCount) {
				/*
				if (this.options.virtualizationMode === "fixed") {
					avgRowHeight = this._calculateAvgRowHeight();
					if  (parseInt(this.options.avgRowHeight, 10) - avgRowHeight > 1) {
						this._scrollContainer().find("div").height(this._totalRowCount * avgRowHeight);
					}
				}
				*/
				if (this._avgRowHeight === undefined || this._avgRowHeight === null) {
					this._avgRowHeight = this._calculateAvgRowHeight();
					/* this._trigger('avgRowHeightChanged', null, { owner: this, oAvgRowHeight: null, avgRowHeight: this._avgRowHeight }); */
				}
				h = this._totalRowCount * this._avgRowHeight;
				/* NOTE(MH): We should not call this._setScrollContainerHeight(h); when virtualization
				is fixed and scrollbar is shown we should NOT use this._setScrollContainerHeight(h);-
				if we set it - then scrolling to the bottom is not correct */
				/* M.H. 12 Jun 2013 Fix for bug #144009: With continuous virtualization rows fill the height of the grid */
				/* M.H. 18 Jul 2013 Fix for bug #146756: On deleting a row, the rows shrink their height
				and occupy only 50% of the grid's height and Average row height doesnot get applied */
				/* M.H. 10 Mar 2015 Fix for bug 190052: When virtualization is enabled and
				there are few records in the grid, their height becomes equal to the grid height */
				/* scrollbar is not shown */
				if (this._scrollContainer().height() >= h) {
					// if height of the table is set then rows will increase their height to fit the table - in this case height should not be set
					e.height("");
					/* continuous virtualization doesn't add .ui-iggrid-virtualrow */
					if (this.options.virtualizationMode === "fixed") {
						e.find(".ui-iggrid-virtualrow").height(this._avgRowHeight);
					}
					/* M.H. 28 Feb 2014 Fix for bug #164139: IGGrid virtualScrollTo
					doesn't seem to be reliable for fixed and continuous virtualization */
					this._setScrollContainerHeight(h);
				} else {
					//A.T.
					if (this.options.virtualizationMode === "continuous") {
						/* M.H. 1 July 2015 Fix for bug 201706: When virtualization is enabled along with
						Filtering,Sorting and GroupBy grouping a column causes the only row in the grid to
						expand to grid`s height when virtualizationMode is continuous it should not be set
						height of the table(otherwise when there are only few rows - their height is
						expanded to fit the height of the table) */
						e.height("");
						/* M.H. 28 Feb 2014 Fix for bug #164139: IGGrid virtualScrollTo
						doesn't seem to be reliable for fixed and continuous virtualization */
						/* M.H. 22 Jul 2013 Fix for bug #146756: On deleting a row, the rows shrink their
						height and occupy only 50% of the grid's height and Average row height doesnot get applied */
						this._setScrollContainerHeight(h);
					} else {
						e.height(this._initialVirtualHeight);
					}
				}
			}
		},
		/* returns true if any of the columns have a template set */
		_hasColumnTemplates: function () {
			var i, hasTemplates = false, t;
			for (i = 0; i < this.options.columns.length; i++) {
				t = this.options.columns[ i ].template;
				if (t !== null && t !== undefined && t !== "" && t.length > 0) {
					hasTemplates = true;
					break;
				}
			}
			return hasTemplates;
		},
		_gridHasWidthInPixels: function () {
			if (typeof this.options.width === "string" && this.options.width.indexOf("%") !== -1) {
				return false;
			}
			return parseInt(this.options.width, 10) > 0;
		},
		_gridHasWidthInPercent: function () {
			if (typeof this.options.width === "string" && this.options.width.indexOf("%") !== -1) {
				return true;
			}
			return false;
		},
		_isColumnVirtualizationEnabled: function () {
			// returns whether column virtualization is enabled
			// it is possible the option columnVirtualization to be set to false BUT virtualization(not rowVirtualization) to be TRUE and width of the columns to be larger of the grid width
			var i, w, o = this.options, cols, allColsInPercentage = true;
			if (!o.virtualization || o.virtualizationMode === "continuous" || o.width === null) {
				return false;
			}
			if (o.columnVirtualization) {
				return true;
			}
			cols = o.columns;
			for (i = 0; i < cols.length; i++) {
				if (cols[ i ].width) {
					w = cols[ i ].width;
				}
				w = _aNull(w) ? o.defaultColumnWidth : w;
				if (!_aNull(w) && ($.type(w) !== "string" || !w.endsWith("%"))) {
					allColsInPercentage = false;
					break;
				}
			}
			if (!allColsInPercentage) {
				return true;
			}
			return false;
		},
		hasVerticalScrollbar: function () {
			/* returns whether there is vertical scrollbar. Because of perfrormance issues in older Internet Explorer especially 8,9 - there is no need to check if height is not set - there is no scrollbar OR if row virtualization is enabled - it is supposed there is vertical scrollbar
				returnType="boolean" returns whether there is vertical scrollbar
			*/
			/* P.Zh. 8 Apr. 2016 Fix for bug 217557: If the grid does
			not have height hasVerticalScrollbar method returns true. */
			if (this.options.height === null || this.options.height === undefined) {
				return false;
			}
			/* A.T. the below line is very slow on IE. Therefore we need
			to do some magic to avoid reflow, which will be caused */
			/* by calling height() on the various elements which have many children.
			height() just calls offsetHeight, which is a performance killer on IE9
			when invoked on elements with many children */
			var hasVScrollbar;
			hasVScrollbar = (this.options.autoAdjustHeight &&
				this.element.height() <= this.scrollContainer().height()) ||
				(!this.options.autoAdjustHeight &&
				this.options.avgRowHeight * this.element[ 0 ].rows.length < parseInt(this.options.height, 10));
			return !hasVScrollbar;
		},
		_adjustLastColumnWidth: function (colgroupsRerendered) {
			// if fixed headers is enabled, we need to make sure the last column's width is adjusted appropriately
			// we have to do this here, because we don't know if we need a scroll bar or not, before data is actually rendered
			// if colgroupsRerendered is true this function should always be called
			// after a corresponding call to renderColgroup as there are fields than need to be calculated there
			var grid = this, calcGridWidth,
				hasFixedHeader = this.options.height !== null &&
				this.options.fixedHeaders === true &&
				this.options.showHeader &&
				this._headerParent,
				hasFixedFooter = this.options.height !== null &&
				this.options.fixedFooters === true &&
				this.options.showFooter &&
				this._footerParent,
				hasHeight = this.options.height && parseInt(this.options.height, 10) > 0,
				hasWidthInPixels = this._gridHasWidthInPixels(),
				hasWidthInPercent = this._gridHasWidthInPercent(),
				hasVirtualization = this.options.virtualization === true ||
				this.options.columnVirtualization === true ||
				this.options.rowVirtualization === true,
				gridWidth,
				specialColumnsWidth;
			if (hasHeight && !hasVirtualization) {
				if (this.hasVerticalScrollbar()) {
					this._hasVerticalScrollbar = true;
				} else {
					this._hasVerticalScrollbar = false;
					/* M.H. 18 Nov 2015 Fix for bug 209777: When the columns have width and
					grid's width option is not set the grid does not have a proper width. */
					if (this._scrollWidthAddedToContainerWidth) {
						this.container().width(this._calculateContainerWidth(false));
					}
				}
			}
			if (colgroupsRerendered) {
				if (hasVirtualization) {
					if (hasFixedHeader) {
						this._headerParent.css(this._padding, this._scrollbarWidth());
					}
					if (hasFixedFooter) {
						this._footerParent.css(this._padding, this._scrollbarWidth());
					}
				}
				if (hasWidthInPixels || hasWidthInPercent) {
					if (this._allColumnWidthsInPixels) {
						gridWidth = this._totalColPixelWidth;
						/* M.H. 18 Apr 2016 Fix for bug 216301: Horizontal scrollbar disappears when
						resizing a column, fixing and unfixing it after that with virtualization */
						/* when there are initially fixed columns grid width shouldn't include width of fixed cols */
						if (this.hasFixedColumns() && !this._initialized) {
							gridWidth = 0;
							/* get sum of visible and ufnixed column width */
							$.each(this.options.columns, function (ind, col) {
								gridWidth += col.fixed || col.hidden ? 0 : (parseInt(col.width, 10) || 0);
							});
						}
						/* take the special columns widths into account */
						specialColumnsWidth = this._calculateSpecialColumnsWidth();
						if (specialColumnsWidth > 0) {
							if (this._autoadjustedColumn && this._lastColPixelWidth) {
								//if the last column was autofited we should substract the
								//special columns width from its width
								// S.S. May 18, 2012 We need to check if the autoadjustedcolumn was not expanded
								// for a smaller amount than what comes from special columns
								if (this._autoadjustedColumn.difference < specialColumnsWidth) {
									this._lastColPixelWidth -= this._autoadjustedColumn.difference;
									gridWidth -= this._autoadjustedColumn.difference - specialColumnsWidth;
								} else {
									this._lastColPixelWidth -= specialColumnsWidth;
								}
								this._autoadjustedColumn.width = this._lastColPixelWidth;

								if (hasFixedHeader) {
									this._lastHeaderCol.css("width", this._lastColPixelWidth + "px");
								}
								if (hasFixedFooter) {
									this._lastFooterCol.css("width", this._lastColPixelWidth + "px");
								}
								this._lastDataCol.css("width", this._lastColPixelWidth + "px");
							} else {
								gridWidth += specialColumnsWidth;
							}
						}
						/* L.A. 05 June 2012. Fixing bug #113545 When grid's width is bigger than the sum of column widths
						and fixedHeaders=false the hidding icon of the last column is not visible.
						L.A. 16 October 2012 - Fixing bug #123050 When showHeader = FALSE and the has a vertical and
						horizontal scrollbars, the last column is 17 pixels less wide than expected
						L.A. 24 October 2012 - Fixing bug #118440 When grid's width = 100%, every column's width is set
						in pixels and the page has vertical scrollbar there is an unnecessary vertical scrollbar. */
						/* M.H. 8 Nov 2012 Fix for bug #126666 */
						if (!hasFixedHeader && this._gridWidthGTColWidth && this._hasVerticalScrollbar) {
							gridWidth -= this._scrollbarWidth();
							this._lastColPixelWidth -= this._scrollbarWidth();
							this._lastDataCol.css("width", this._lastColPixelWidth + "px");
						}
					} else if (this._allColumnWidthsInPercentage) {
						gridWidth = this.container().width();
						specialColumnsWidth = this._calculateSpecialColumnsWidth();
						if (this._allSpecialColumnsInPercentage() === true && specialColumnsWidth > 0) {
							this._lastColPercentWidth = this._lastColPercentWidth - specialColumnsWidth;
						}
						this._lastDataCol.css("width", this._lastColPercentWidth + "%");
						if (hasFixedHeader) {
							this._lastHeaderCol.css("width", this._lastColPercentWidth + "%");
						}
						if (hasFixedFooter) {
							this._lastFooterCol.css("width", this._lastColPercentWidth + "%");
						}
					} else if (hasWidthInPixels) {
						// M.H. 31 August 2015 Fix for bug 203683: Scrollbar's thumb moving back and forth horizontally when using continuous virtualization mode
						calcGridWidth = this._calculateContainerWidth(false);
						gridWidth = parseInt(grid.options.width, 10);
						if (calcGridWidth > gridWidth) {
							gridWidth = calcGridWidth;
						}
					} else if (hasWidthInPercent) {
						gridWidth = this.container().width();
					}

					if (!this._allColumnWidthsInPercentage || !hasWidthInPercent) {
						if (hasVirtualization === false) {
							this._gridInnerWidth = this.scrollContainer().width();
						} else {
							this._gridInnerWidth = //this.element.width() + this._scrollbarWidth();
								this._vdisplaycontainer().width() + this._scrollbarWidth();
						}
						/* M.H. 17 May 2013 Fix for bug #140364: Text is covered over by the scroll bar when
						text is right aligned in Chrome width of the cell is not calculated properly in this
						case(width is not set for the cols) and padding is not properly set */
						/* M.H. 12 Nov 2012 Fix for bug #127037 */
						grid._setGridContentWidth(gridWidth);
						/* M.H. 7 Mar 2016 Fix for bug 214909: Horizontal scrollbar changes width
						when a column is fixed and commit() is called in IE when call
						_setGridContentWidth it is possible to be shown vertical scrollbar */
						if (!this._hasVerticalScrollbar && this.hasVerticalScrollbar() &&
							hasHeight && !hasVirtualization) {
							this._hasVerticalScrollbar = true;
							grid._setGridContentWidth(gridWidth);
						}
					}
				}
			} else {
				// even if columns are not rerendered we need to adjust the size of the horizontal
				// scrollbar as this is called when height is adjusted which might render a vertical
				// scrollbar it wasn't taken into account in previous calculations.
				this._setGridContentWidth();
			}
			if (hasHeight && !hasVirtualization) {
				if (this.options.width !== null && this.options.height !== null) {
					if (this._hasVerticalScrollbar) {
						this._hscrollbarcontent().css("overflow-y", "scroll");
					} else {
						this._hscrollbarcontent().css("overflow-y", "hidden");
					}
				}
				if ((this._allColumnWidthsInPercentage && hasWidthInPercent) ||
					!(hasWidthInPixels || hasWidthInPercent)) {
					//if the grid has vertical scrollbar and no width or width in percent
					//and column widths in percent we should add padding
					//to the header and footer tables to align the columns properly
					if (this._hasVerticalScrollbar) {
						if (hasFixedHeader) {
							this._headerParent.css(this._padding, this._scrollbarWidth());
						}
						if (hasFixedFooter) {
							this._footerParent.css(this._padding, this._scrollbarWidth());
						}
					} else {
						if (hasFixedHeader) {
							this._headerParent.css(this._padding, "");
						}
						if (hasFixedFooter) {
							this._footerParent.css(this._padding, "");
						}
					}
				} else {
					//in all other casese the scrollbar is inside the last data column
					//and we need to add padding in the header/footer cells to align the icons
					//such that they do not go over the vertical scrollbar
					this._updateVerticalScrollbarCellPadding();
				}
			}
			if (hasWidthInPercent && hasVirtualization) {
				this._updateVerticalScrollbarCellPadding();
			}
		/* M.H. 6 Jun 2016 Fix for bug 220191: When rowselectors are enabled and grid width is greater than sum of column widths and autofitLastColumn is true horizontal scrollbar is shown */
			this._trigger("_lastColumnWidthAutoAdjusted", null, {
				specialColumnsWidth: specialColumnsWidth
			});
		},
		_initializeHeights: function () {
			// A.T. 6 April 2011 - changing this logic so that it's executed async in the background. This is just for re-setting the proper height of the scroll container,
			// in case there are other elements that take up space, such as paging footers, filter summaries, etc.
			// with this approach , including setting overflow hidden to the container, and initializing the scroll container with the height of its parent, we don't get
			// the nasty visual flickerings and resetting of the scrollbar
			if (this.options.height === null || this.options.height === undefined) {
				return;
			}
			if (this.options.height.indexOf && this.options.height.indexOf("%") !== -1) {
				// M.H. 4 Apr 2016 Fix for bug 217213: Grid height is not correctly calculated when set in %
				/*this.scrollContainer().height("100%");*/
				if (this._isWrapped && !this._initialized) { // in case of igGrid is instantiated on DIV
					this.container().parent().height(this.options.height);
					this.container().height("100%");
				}
			} else if (!this.hasFixedColumns()) {
				this.scrollContainer().height(parseInt(this.options.height, 10));
			}
			this._initializeHeightsInternal();
		},
		_initializeHeightsInternal: function () {
			var children, height, i, $child, ch, heightChanged = false, dc = this._vdisplaycontainer(),
				vhcntnr, sc = this._scrollContainer();
			ch = this.container().height();
			this._prevContainerHeight = ch;
			if (this.options.height !== null) {
				children = this.container().children(":visible");
				height = 0;
				for (i = 0; i < children.length; i++) {
					$child = $(children[ i ]);
					/* also check for the "excludeFromHeight" attribute. Such elements are
					filter dropdowns, which are children of the grid container and may appear
					visible once they are open, but we don't want to include them in any calculations */
					/* M.H. 28 Feb 2013 Fix for bug #134435: When height is set and column is fixed
					changing the page changes grid"s height and the horizontal scrollbar is not on the correct place */
					if ((($child.attr("id") !== undefined &&
						!$child.attr("id").endsWith("_scroll") &&
						!$child.attr("data-fixed-container") &&
						!$child.attr("id").endsWith("_virtualContainer") &&
						!$child.attr("id").endsWith("_loading")) ||
						!$child.attr("id")) && $child.is(":visible") &&
						$child.data("efh") !== "1") {
						// the caption's table shows height of 0 on all browsers !
						if ($child.is("table") && $child.children().first().is("caption")) {
							height += $child.children().first().outerHeight();
						} else {
							if ($child.css("position") !== "absolute") {
								height += $child.outerHeight();
							}
						}
					}
				}
				if (height > 0) {
					/* M.H. 7 Mar 2014 Fix for bug #147490: The "Search Result" span is
					relocated when column is fixed and you filter the grid in Chrome. */
					this._trigger("_heightChanging", null, { ch: ch, h: height });
					this.scrollContainer().height(ch - height);
					heightChanged = true;
				}
				/* if there is virtualization, also adjust its containers accordingly */
				if (this.options.virtualization || this.options.rowVirtualization) {
					/* M.H. 19 Feb 2015 Fix for bug #187756: When row selectors and fixed
					virtualization are enabled fixing a column moves the horizontal scrollbar downwards. */
					/*if (this.hasFixedColumns()) {
						height += this._hscrollbar().height();
					} */
					/* exclude headers & footers (if any) */
					/* M.H. 9 Apr 2013 Fix for bug #139223: When virtualization
					is enabled the grid starts expanding its height. */
					if (($.type(this.options.height) === "string" &&
						this.options.height.indexOf("%") !== -1) ||
						ch !== parseInt(this.options.height, 10)) {
						this.container().height(this.options.height);
						if ($.type(this.options.height) === "string" &&
							this.options.height.indexOf("%") !== -1) {
							ch = parseInt(this.container().height(), 10);
						}
					}
					height += this.container().find("#" + this.id() + "_headers_v").outerHeight();
					height += this.container().find("#" + this.id() + "_footers_v").outerHeight();
					/* M.H. 24 Jun 2013 Fix for bug #145329: When virtualization is enabled gridVirtual
					container is bigger than grid container, because it includes scrollbars in its height */
					height += this._fixedfooters().outerHeight();
					/* M.H. 20 Apr 2016 Fix for bug 218362: Height of the grid is incorrect after resizing a column */
					vhcntnr = this._vhorizontalcontainer();
					height += vhcntnr.is(":visible") ? vhcntnr.outerHeight() : 0;
					if (!this._virtualHeightReset) {
						height = height * 2;
						this._virtualHeightReset = true;
					}
					if (height > 0 && dc.height() !== ch - height) {
						// we should not call it twice
						/* M.H. 7 Mar 2014 Fix for bug #147490: The "Search Result" span is
						relocated when column is fixed and you filter the grid in Chrome. */
						if (!heightChanged) {
							this._trigger("_heightChanging", null, { ch: ch, h: height });
						}
						dc.height(ch - height);
						sc.height(ch - height);
						/* M.H. 1 July 2015 Fix for bug 201706: When virtualization is enabled along with
						Filtering,Sorting and GroupBy grouping a column causes the only row in the grid to expand to grid`s height */
						if (!this.options.rowVirtualization) {/*  || this.options.virtualizationMode !== "continuous" */
							this.element.height(ch - height);
						}
						this._initialVirtualHeight = ch - height;
						/* M.H. 12 Mar 2014 Fix for bug #166434: VirtualScrollTo inconsistent with pixels input and fixed virtualization */
						/* this._virtualDom = null; */
						/* M.H. - calling renderVirtualRecords causes js error and breaks the whole grid */
						/* this._renderVirtualRecords(); */
						heightChanged = true;
					}
				}
				if (heightChanged) {
					// M.H. 4 Apr 2016 Fix for bug 217213: Grid height is not correctly calculated when set in %
					this._prevContainerHeight = this.container().height();
					this._trigger("_heightChanged", null, { ch: ch, h: height });
				}
			}
		},
		_registerAdditionalEvents: function () {
			// M.H. 29 Jul 2014 Fix for bug #176483: When sorting a column in the grid memory is leaked.
			if (this._hovEvts) {
				return;
			}
			var self = this;
			if (this.options.enableHoverStyles) {
				this._hovEvts = {
					mousemove: function (e) {
						var par, tag, tr = e.target;
						while (tr) {
							par = tr.parentNode;
							tag = tr.nodeName;
							if (tag === "TR" && par.nodeName === "TBODY") {
								break;
							}
							tr = (tag === "TABLE") ? null : par;
						}
						self._mousemoveTr(tr, e);
					},
					mouseleave: function () {
						self._mouseleaveTr();
					}
				};
				this.element.bind(this._hovEvts);
			}
		},
		_mousemoveTr: function (tr, e) {
			var css = "ui-state-hover", topmostGrid, $tr = $(tr);
			if (_hovTR !== tr) {
				// A.T. data-container=true => child row container. don't hover such rows.
				if (_hovTR && $(_hovTR).attr("data-container") !== "true") {
					$("td", _hovTR).removeClass(css);
				}
				/* I.I. bug fix for 100872 (if virtualization is on for child grid,
				hovering the scroller should not hover all the child grid"s tds */
				if (tr && $tr.attr("data-container") !== "true" &&
						e.target.id.indexOf("_scrollContainer") === -1 &&
						e.target.parentNode.id.indexOf("_scrollContainer") === -1) {
					topmostGrid = this.element.closest(".ui-iggrid-root").data("igGrid") || this;
					tr = $tr.add($tr.siblings("[data-id='" + $tr.attr("data-id") + "']"));
					if (!topmostGrid._cancelHoveringEffects) {
						$("td", tr).addClass(css);
					}
				}
				_hovTR = tr;
			}
		},
		_mouseleaveTr: function () {
			var css = "ui-state-hover";
			if (_hovTR) {
				$("td", _hovTR).removeClass(css);
				_hovTR = null;
			}
		},
		_renderColgroup: function (table, isHeader, isFooter, autofitLastColumn, md) {
			var colgroup, hasFixedColumns = this.hasFixedColumns(),
				i, lc, fixed = !!(md && md.fixed), f,
				cols = this._visibleColumns(), col,
				defWidth = this.options.defaultColumnWidth,
				totalColWidth = 0,
				totalGridWidth,
				hasWidthInPixels = this._gridHasWidthInPixels(),
				hasWidthInPercent = this._gridHasWidthInPercent(),
				hasWidth = hasWidthInPixels || hasWidthInPercent,
				hasVirtualization = this.options.virtualization === true ||
				this.options.columnVirtualization === true ||
				this.options.rowVirtualization === true,
				/* I.I. bug fix for 107132 */
				hasColumnVirtualization = (this.options.virtualization === true &&
				this.options.virtualizationMode === "fixed") ||
				this.options.columnVirtualization === true,
				hasColumnsWithNoWidth = false,
				isPercentage = true,
				desiredColWidth,
				desiredColWidthNumber,
				lastCol,
				lastColWidth,
				difference;
			/* we render colgroup if there are columns defined and autoGenerateColumns is false */
			colgroup = $(table).find(">colgroup");
			if (colgroup.length === 0) {
				colgroup = $("<colgroup></colgroup>").prependTo(table);
			}
			/* if we use virtualization we need cols just for the number of visible columns */
			/* M.H. 10 April 2012 Fix for bug #107566 */
			if ((hasVirtualization &&
				this.options.virtualizationMode === "fixed") ||
				hasColumnVirtualization) {
				this._updateVirtColCounters();
				cols = cols.slice(0, this._virtualColumnCount);
			}
			for (i = 0; i < cols.length; i++) {
				if (this._isMultiRowGrid()) {
					col = $.extend(true, {}, cols[ i ]);
				} else {
					col = cols[ i ];
				}
				f = !!col.fixed;
				if (f !== fixed) {
					continue;
				}
				if (this._rlp && i >= this._maxCols) {
					break;
				}
				if (this._colGroupWidths) {
					col.width = this._colGroupWidths[ i ];
				}
				if (col.width || col.width === 0) {
					/* M.H. 16 Dec 2013 Fix for bug #154835: In Internet Explorer 8 when the
					grid has 100% width,  filtering is enabled and a column is hidden there
					is a blank space on the right of the grid. */
					if (col.oWidth) {
						desiredColWidth = col.oWidth;
					} else {
						desiredColWidth = col.width;
					}
				} else {
					desiredColWidth = defWidth;
					col.width = defWidth;
				}
				if (desiredColWidth || desiredColWidth === 0) {
					if (!desiredColWidth.charAt || !desiredColWidth.endsWith("%")) {
						isPercentage = false;
					}
					desiredColWidthNumber = parseInt(desiredColWidth, 10);
					if (desiredColWidthNumber < 0 || isNaN(desiredColWidthNumber)) {
						desiredColWidthNumber = undefined;
					}
				} else {
					desiredColWidthNumber = undefined;
					isPercentage = false;
				}
				if (!_aNull(desiredColWidthNumber)) { //desired column width is defined as pixels or percentage
					lastCol = $("<col></col>").appendTo(colgroup).css("width", desiredColWidth);
					lastColWidth = desiredColWidthNumber;
					/* calculating the total sum of all widths or percentages */
					totalColWidth += desiredColWidthNumber;
					/* M.H. 25 Sep 2014 Fix for bug #179192: The width returned by
					columnByKey method is a number even though it's set as a string in pixels. */
					if ($.type(col.width) === "string" && col.width.endsWith("px")) {
						col.width = desiredColWidthNumber + "px";
					} else {
						col.width = desiredColWidthNumber;
					}
					/* M.H. 26 Feb 2014 Fix for bug #165394: Last column occupies the width of the grid
					when there is a hidden column and the widths of the columns are set in percentage */
					if (desiredColWidth && desiredColWidth.charAt && desiredColWidth.endsWith("%")) {// is percentage
						col.width = col.width + "%";
					}
				} else {
					lastCol = $("<col></col>").appendTo(colgroup);
					lastColWidth = undefined;
					hasColumnsWithNoWidth = true;
				}
				lc = col;
			}
			/* M.H. 21 Oct 2014 Fix for bug #182454: When the width of the grid with
			columnVirtualization is big enough so there is no horizontal scrollbar
			hiding a column cause a misalignment in Firefox */
			if ((!hasColumnVirtualization || this._vhorizontalcontainer().find("div").length === 0) &&
				autofitLastColumn && hasWidth && !hasColumnsWithNoWidth && lastColWidth) {
				// account for the special case where the grid has a width defined and widths defined for each column
				// but the total sum of column widths is less than the width of the grid(or not 100 for percentages),
				// then we need to "expand" the last column to fill in the remaining blank space (so that it is not blank)
				if (isPercentage) {
					if (totalColWidth < 100) {
						lastColWidth += 100 - totalColWidth;
						lastCol.css("width", lastColWidth + "%");
						/* set the new width as last column width */
						this._autoadjustedColumn = cols.slice(-1)[ 0 ];
						/* M.H. 16 Dec 2013 Fix for bug #154835: In Internet Explorer 8
						when the grid has 100% width,  filtering is enabled and a column
						is hidden there is a blank space on the right of the grid. */
						if (!this._autoadjustedColumn.oWidth) {
							this._autoadjustedColumn.oWidth = this._autoadjustedColumn.width;
						}
						this._autoadjustedColumn.width = lastColWidth + "%";
						lc.width = lastColWidth + "%";
					}
				} else {
					if (hasWidthInPixels) {
						totalGridWidth = parseInt(this.options.width, 10);
					} else if (hasWidthInPercent) {
						/* M.H. 31 May 2016 Fix for bug 219550: Empty horizontal scrollbar appears when the grid is bigger than the column sizes and width is set in percents. */
						totalGridWidth = parseInt(this.container()[ 0 ].getBoundingClientRect().width, 10);
					}
					if (hasFixedColumns && totalGridWidth) {
						// M.H. 18 Feb 2014 Fix for bug #164303: When a column is fixed and autofitLastColumn is true hiding the last unfixed column increases the width of the column next to it.
						// M.H. 19 Feb 2014 Fix for bug #164599: When column is fixed and you hide enough column so the grid does not need a horizontal scrollbar but there is one anyway.
						if (hasWidthInPercent && this.element[ 0 ].style.width.indexOf("px") > 0) { // width of grid element is already updated in column fixing code(in func _updateGridWidth when grid width is %)
							// M.H. 12 Apr 2016 Fix for bug 214618: When fixing and unfixing the last column on a resized window in a grid with 100% width the column widths are changed compared to their initial widths
							totalGridWidth = parseInt(this.element[ 0 ].style.width, 10);
						} else {
							/* M.H. 6 Jun 2016 Fix for bug 220191: When rowselectors are enabled and grid width is greater than sum of column widths and autofitLastColumn is true horizontal scrollbar is shown */
							for (i = 0; i < cols.length; i++) {
								if (cols[ i ].fixed && cols[ i ].width && !cols[ i ].hidden) {
									totalGridWidth -= parseInt(cols[ i ].width, 10);
								}
							}
						}
						totalGridWidth -=
							hasVirtualization && this.fixingDirection() === "right" && !fixed ?
							parseFloat(this._virtualcontainer().children("colgroup").children().last().css("width")) :
							0;
					}
					/* M.H. 27 Oct 2014 Fix for bug #183041: After showing a hidden
					column in unfixed table - width of the table is not correct */
					this._gridWidthGTColWidth = false;
					if (totalGridWidth && totalGridWidth > totalColWidth) {
						difference = totalGridWidth - totalColWidth;
						lastColWidth += difference;
						totalColWidth += difference;
						lastCol.css("width", lastColWidth + "px");
						/* set the new width as last column width */
						this._autoadjustedColumn = lc;//cols.slice(-1)[0];
						/* M.H. 16 Dec 2013 Fix for bug #154835: In Internet Explorer 8
						when the grid has 100% width,  filtering is enabled and a column
						is hidden there is a blank space on the right of the grid. */
						if (!this._autoadjustedColumn.oWidth) {
							this._autoadjustedColumn.oWidth = this._autoadjustedColumn.width;
						}
						this._autoadjustedColumn.width = lastColWidth + "px";
						this._autoadjustedColumn.difference = difference;
						lc.width = lastColWidth + "px";
						/* L.A. 16 October 2012 - Fixing bug #123050 When showHeader = FALSE and the has a vertical and
						horizontal scrollbars, the last column is 17 pixels less wide than expected */
						this._gridWidthGTColWidth = true;
					} else {
						/* M.H. 10 Mar 2016 Fix for bug 215039: Hiding and unhiding a column
						with row selectors enabled doesn't preserve initially set width of the column */
						this._autoadjustedColumn = undefined;
					}
				}
			} else {
				this._autoadjustedColumn = undefined;
			}
			this._allColumnWidthsInPercentage = isPercentage && !hasColumnsWithNoWidth && cols.length > 0;
			this._allColumnWidthsInPixels = !isPercentage && !hasColumnsWithNoWidth && cols.length > 0;
			/* A.Y. bug 102220. When the grid has column virtualization and width
			the scrollbar's width should be substracted from the last column's width */
			/* M.H. 2 April 2012 Fix for bug #107567 - it should
			not be only for isHeader(it should be for footer too) */
			/* S.S. May 17, 2012 The vertical scrollbar should be substracted from the
			last column's width at all times otherwise the user needs to account for
			it's presence to determine a proper width for his grid and not get a horizontal
			scroll bar (either virtual or normal) */
			if (hasWidth && hasVirtualization && !hasColumnsWithNoWidth && lastColWidth) {
				if (!isPercentage && (!hasFixedColumns || (!fixed && this.fixingDirection() === "left"))) {
					/* M.H. 11 Jan 2016 Fix for bug 212105: Option autofitLastColumn:false
					is not working properly when rowVirtualization is enabled and total
					width of the columns is less than grid width */
					/* do not apply autofitLastColumn when rowVirtualization is enabled,
					autofitLastColumn is FALSE and sum of the widths of all columns is
					less than width of the grid */
					if (this.options.autofitLastColumn ||
						!this.options.rowVirtualization ||
						this._applyAutofitLastColInVirtGrid(totalColWidth)) {
						lastColWidth -= this._scrollbarWidth();
						lastCol.css("width", lastColWidth + "px");
						/* M.H. 18 May 2016 Fix for bug 219547: Fixing the last column when having continuous virtualization, grid width in pixels and fixingDirection right.*/
						if (this._autoadjustedColumn) {
							lc.width = lastColWidth + "px";
						}
					}
				}
			}
			if (isHeader) {
				this._lastHeaderCol = lastCol;
			}
			if (isFooter) {
				this._lastFooterCol = lastCol;
			}
			if (!isHeader && !isFooter) {
				this._lastDataCol = lastCol;
			}
			if (this._allColumnWidthsInPixels) {
				this._lastColPixelWidth = lastColWidth;
				this._totalColPixelWidth = totalColWidth;
			} else if (this._allColumnWidthsInPercentage) {
				this._lastColPercentWidth = lastColWidth;
				this._totalColPixelWidth = undefined;
			} else {
				this._lastColPixelWidth = undefined;
				this._totalColPixelWidth = undefined;
			}
		},
		_renderRecordsForTable: function (start, end, table, tbody, isFixed) {
			var i, d = "", ds = this._getDataView(),
				grid = this, tbodytmp, ph, rrFunc;
			if (this._rlp) {
				rrFunc = grid._renderRecordFromLayout;
			} else {
				rrFunc = grid._renderRecord;
			}
			tbody = tbody || table.children("tbody");
			/* M.H. 23 Feb 2016 Fix for bug 213478: An error is thrown after grouping
			columns from the second and third level of the igHierarchicalGrid hierarchy */
			if (!tbody.length) {
				return;
			}
			for (i = start; i <= end; i++) {
				d += rrFunc.apply(this, [ ds[ i ], i, isFixed ]);
			}
			if (!this._canreplaceinner) {
				tbody.unbind();
				ph = document.createElement("div");
				ph.innerHTML = "<table><tbody class='" + this.css.baseContentClass + " " +
					this.css.gridTableBodyClass + " " + this.css.recordClass +
					"' role='rowgroup'>" + d + "</tbody></table>";
				tbodytmp = ph.firstChild.firstChild;
				table[ 0 ].replaceChild(tbodytmp, tbody[ 0 ]);
			} else if (this._canreplaceinner) {
				MSApp.execUnsafeLocalFunction(function () {
					tbody[ 0 ].innerHTML = d;
				});
			}
			d = "";
		},
		_renderRecords: function (start, end) {
			var tbody = this.element.children("tbody"), noCancelInternal = true,
				ds = this._getDataView(), noCancel = true;
			/* S.S. August 21, 2012, Bug #113279 Adding the continuous virtualization
			markers to the event args so features that need those to render correctly can use them */
			/* S.S. February 11, 2013, Bug #130308 Adding an internal event to hook renderRecord()
			overriding widgets to, as using the same event groupby cancels makes feature initialization order matter */
			/* M.H. 8 Oct 2014 Fix for bug #182146: When Grouping in the
			hierarchical grid, rowsRendered is fired before rowsRendering */
			noCancelInternal = this._trigger("_rowsRendering", null, {
				owner: this,
				tbody: tbody,
				vrtWnd: {
					start: start,
					end: end
				}
			});
			if (noCancelInternal) {
				noCancel = this._trigger(this.events.rowsRendering, null, {
					owner: this,
					tbody: tbody,
					vrtWnd: {
						start: start,
						end: end
					}
				});
				if (start === undefined) { //no params => render all
					start = 0;
					end = ds.length - 1;
				}
				if (start !== undefined && end === undefined) {
					end = start;
					if (end > ds.length - 1) {
						end = ds.length - 1;
					}
					start = 0;
				}
				if (start < 0) {
					start = 0;
				}
				if (end > ds.length - 1) {
					end = ds.length - 1;
				}
				if (noCancel) {
					this._renderRecordsForTable(start, end, this.element);
					/* _virtualDom should be built because features like selection expects it */
					this._buildVirtualDomForContinuousVirtualization();
					this._trigger(this.events.rowsRendered, null, { owner: this, tbody: tbody });
					/* M.K. persist virtual scroll top */
					if ((this.options.virtualization === false ||
						this.options.rowVirtualization === false) &&
						this._persistVirtualScrollTop &&
						this._prevFirstVisibleTROffset &&
						this.scrollContainer().length > 0) {
						this.scrollContainer()[ 0 ].scrollTop = this._prevFirstVisibleTROffset;
					}
				}
			}
		},
		autoSizeColumns: function () {
			/* auto resize columns that have property width set to "*" so content to be auto-fitted(not shrinked/cutted). Auto-resizing is applied ONLY for visible columns*/
			var cols = this._visibleColumns(), fCols = [], ufCols = [], self = this;
			$.each(cols, function (ind, col) {
				var colObj;
				if ((col.width === "*" || col._oWidth === "*") && !col.hidden) {
					colObj = { column: col, visibleIndex: self.getVisibleIndexByKey(col.key) };
					if (col.fixed) {
						fCols.push(colObj);
					} else {
						ufCols.push(colObj);
					}
				}
			});
			/* if there are columns to resize apply auto-resizing - detect their
			height and re-render colgroups - after that update grid content
			width(based on visible columns width) */
			if (fCols.length || ufCols.length) {
				this._calculateAutoResizableWidths(ufCols);
				this._calculateAutoResizableWidths(fCols, true);
				this._rerenderColgroups(false);
				this._updateGridContentWidth();
			}
		},
		_calculateAutoResizableWidths: function (columns, fixed) {
			// columns is array of object - the object has properties - column: <data column object>, visibleIndex
			// it calculates the minimal width of the column so its content for each rows to be visible(not shrinked/cutted) - similar to auto resize in grid resizing
			// because of the performance issues - we have optimized it as rendering the whole data table in hidden div(css property set to resize:both and width:auto) and get width only of the target columns(specified by visibleIndex)
			// NOTE: initial columnfixing is not applied yet so we get data columns directly from this.element data table
			if (!columns || !columns.length) {
				return [];
			}
			var rowsContainer = fixed ? $("#" + this.id() + "_fixed") : this.element,
				$parentDiv, col, cell, w, clientRect, $trs,
				i, index, $firstRowCells, $table, $measureDiv, html, $thead, $tfoot;
			$parentDiv = rowsContainer.closest("div");
			html = $parentDiv.html();
			$measureDiv = $("<div></div>")
				.attr("style", $parentDiv.attr("style"))
				.attr("class", $parentDiv.attr("class") + " " +
				this.container().attr("class") + " " +
				this.css.gridMeasurementContainerClass);
			$measureDiv
				.css({
					"position": "absolute",
					"resize": "both",
					"visibility": "hidden",
					"height": "auto",
					"width": "auto",
					"left": -6000,
					"top": -6000
				})
				.appendTo($(document.body));
			$measureDiv[ 0 ].innerHTML = html;
			$table = $measureDiv.find(">table");
			/* if the grid is already initialized preset widths should be
			removed for auto-size columns from <col> in <colgroup> */
			if (this._initialized) {
				$table.find(">colgroup>col:not([data-skip])").each(function (ind, c) {
					c.style.width = "";
					c.width = "";
				});
			}
			/* M.H. 17 June 2015 Fix for bug 201194: When the grid has height the
			autosizing of the columns does not take into account the header text. */
			if (this.options.showHeader) {
				$thead = fixed ? this.fixedHeadersTable() : this.headersTable();
				/* in case of fixedHeaders is false*/
				$table.find(">thead").remove();
				$trs = $thead.find(">thead")
						.find(">tr[data-header-row],>tr[data-mch-level]");
				if ($trs.length) {
					html = "";
					$trs.each(function (ind, tr) {
						html += tr.outerHTML;
					});
					$("<thead>" + html + "</thead>").appendTo($table);
					$table.find(">thead>tr").children("th,td").css("width", "");
				}
			}
			/* M.H. 17 June 2015 Fix for bug 201194: When the grid has height the
			autosizing of the columns does not take into account the header text. */
			/* take into account footers when calculating "*" col widths */
			if (this.options.showFooter) {
				$tfoot = fixed ? this.fixedFootersTable() : this.footersTable();
				/* in case of fixedFooters is false */
				$table.find(">tfoot").remove();
				$tfoot = $tfoot.find(">tfoot");
				if ($tfoot.length) {
					$($tfoot[ 0 ].outerHTML).appendTo($table);
					$table.find(">tfoot>tr").children("td").css("width", "");
				}
			}
			$table
				.css("width", "auto")
				.attr("id", $table.attr("id") + "__");
			/* get only data cell of the first data row */
			$firstRowCells = $table
				.find(">tbody>tr:not([data-grouprow='true']):first")
				.children("td:not([data-skip]):not([data-parent])");
			/* if grid is bount to the empty data source then get children of the
			first visible row(if any) - taking into account <thead> and/or <tfoot> */
			if (!$firstRowCells.length) {
				$firstRowCells = $table
					.find("tr:visible:not([data-grouprow='true']):first")
					.children("th:not([data-skip]):not([data-parent]), td:not([data-skip]):not([data-parent])");
			}
			if (!$firstRowCells.length) {
				return;
			}
			for (i = 0; i < columns.length; i++) {
				col = columns[ i ].column;
				index = columns[ i ].visibleIndex;
				cell = $firstRowCells[ index ];
				clientRect = cell.getBoundingClientRect();
				w = clientRect.width;
				/* in IE(probably in FF) the width could be returned in floating pixels - we need to get ceil value */
				/* M.H. 17 June 2015 Fix for bug 201177: Auto sizing of the columns is not working in IE8. */
				/* In IE8 or in IE8 mode clientRect object hasn't properties for width and height */
				if (w !== undefined) {
					col.width = Math.ceil(w);
				} else {
					col.width = Math.ceil(clientRect.right - clientRect.left);
				}
				/*if (isNaN(col.width)) {
					col.width = $(cell).outerWidth();
				} */
				col._oWidth = "*";
			}
			$measureDiv.remove();
			return columns;
		},
		calculateAutoFitColumnWidth: function (columnIndex) {
			/*calculates the width of the column so its content to be auto-fitted to the width of the data in it(the content should NOT be shrinked/cutted)
			paramType="number" Visible column index
			returnType="number" Calculated auto-fitted width(-1 if there isn't visible column with the specified columnIndex)
			*/
			var res, col = this._visibleColumns()[ columnIndex ];
			if (!col) {
				return -1;
			}
			/* M.H. 28 Apr 2016 Fix for bug 216219: Auto-sizing a column with
			double mouse click doesn't take into account the column header text */
			res = this._calculateAutoResizableWidths([{
				column: $.extend(true, {}, col),
				visibleIndex: this.getVisibleIndexByKey(col.key)
			}], !!col.fixed);
			return res[ 0 ].column.width;
		},

		_reapplyZebraStyle: function (from) {
			var funcApplyStyles, grid = this;
			if (!this.options.alternateRowStyles) {
				return;
			}
			funcApplyStyles = function (dataRows) {
				var toChange;
				from = from || 0;
				toChange = dataRows.slice(from);
				toChange.filter(from % 2 === 0 ? ":odd" : ":even")
					.addClass(grid.css.recordAltClass);
				toChange.filter(from % 2 === 0 ? ":even" : ":odd")
					.removeClass(grid.css.recordAltClass);
			};
			funcApplyStyles(this.element.children("tbody")
				.children("tr:not([data-container],[data-grouprow])"));
			if (this.hasFixedColumns()) {
				funcApplyStyles(this.fixedContainer().find("tbody")
					.children("tr:not([data-container],[data-grouprow])"));
			}
		},
		_buildFormatters: function () {
			var i, cols = this.options.columns, ret = {};
			for (i = 0; i < cols.length; i++) {
				if (cols[ i ].formatter !== undefined) {
					if ($.type(cols[ i ].formatter) === "function") {
						ret[ cols[ i ].key + "Formatter" ] = cols[ i ].formatter;
					} else if (window[ cols[ i ].formatter ] &&
						typeof window[ cols[ i ].formatter ] === "function") {
						ret[ cols[ i ].key + "Formatter" ] = window[ cols[ i ].formatter ];
					}
				}
			}
			return ret;
		},
		getVisibleIndexByKey: function (columnKey, includeDataSkip) {
			/*Get visible index by specified column key. If column is not found or is hidden then returns -1.
				Note: Method does not count column groups (Multi-Column Headers).
				paramType="string" columnKey
				paramType="bool" Optional parameter - if set to true include non data columns(like expander column, row selectors column, etc.) in calculations
				returnType="number" returns visible index. If column is not found or column is hidden returns -1
			*/
			var hasFixedColumns = this.hasFixedColumns(), fixedCounters, unfixedCounters, prevColumn,
				cols = this._visibleColumns(), index = -1, fixed = false, $colgroup;
			if (hasFixedColumns) {
				fixedCounters = 0;
				unfixedCounters = 0;
				prevColumn = null;
				$.each(cols, function (ind, value) {
					var isFixed = (value.fixed === true),
						prevColumnFixed = (prevColumn && (prevColumn.fixed === true));
					if (prevColumn === null || prevColumnFixed !== isFixed) {
						if (value.fixed === true) {
							fixedCounters = 0;
						} else {
							unfixedCounters = 0;
						}
					}
					prevColumn = value;
					if (value.hidden) {
						return true;
					}
					if (isFixed !== true) {
						unfixedCounters++;
					} else {
						fixedCounters++;
					}
					if (value.key === columnKey) {
						if (isFixed) {
							fixed = true;
							index = fixedCounters - 1;
						} else {
							index = unfixedCounters - 1;
						}
						if (index === -1) {
							index = 0;
						}
						return false;
					}
				});
			} else {
				$.each(cols, function (ind, value) {
					if (value.key === columnKey) {
						index = ind;
						return false;
					}
				});
			}
			if (index === -1) {// if col with the specified colKey is not found or is hidden
				return -1;
			}
			if (includeDataSkip) {
				if (fixed) {
					$colgroup = $("#" + this.id() + "_fixed")
									.find("colgroup:first");
				} else {
					$colgroup = this.element
									.find("colgroup:first");
				}
				index += $colgroup.children("col[data-skip]").length;
			}
			return index;
		},
		_isColumnHidden: function (columnKey) {
			/* returns if column is hidden by column key */
			var cols = this.options.columns, i = cols.length;
			while (i-- >= 0) {
				if (cols[ i ].key === columnKey) {
					return cols[ i ].hidden || false;
				}
			}
			return true;
		},
		_visibleColumns: function (isFixed, cols) {
			cols = cols || this.options.columns;
			/* isFixed is passed as an argument then it is supposed there are
			fixed columns - if true then return visible fixed columns, if false
			visible unfixed columns when isFixed is not passed then it works as
			till now - returns cached _visibleColumnsArray if set otherwise
			populate it and returns it */
			if (isFixed !== undefined) {
				return $.grep(cols, function (col) {
					var fixed = (col.fixed === true);
					return !col.hidden && fixed === isFixed;
				});
			}
			if (this._visibleColumnsArray === undefined) {
				//performance optimization as visibleColumns is called a lot
				this._visibleColumnsArray = $.grep(cols, function (col) { return !col.hidden; });
			}
			return this._visibleColumnsArray;
		},
		_visibleMchColumns: function (curLvl) {
			var vmch = [], cl = curLvl || this._oldCols, self = this;
			if (!cl) {
				return null;
			}
			$.each(cl, function () {
				if (!this.hidden || this.hidden === false) {
					vmch.push($.extend({}, this));
					if (this.group) {
						vmch[ vmch.length - 1 ].group = self._visibleMchColumns(this.group);
					}
				}
			});
			return vmch;
		},
		_renderVirtualRecordsFixed: function () {
			var noVirtualDom = (this._virtualDom === null || this._virtualDom === undefined);
			if (this.hasFixedColumns()) {
				this._updateVirtColCounters(true);
				this._renderVirtualRecordsFixedInternal(true);
				this._updateVirtColCounters(false);
			}
			this._renderVirtualRecordsFixedInternal(false);
			/* M.H. 19 Dec 2013 Fix for bug #160188: Selecting a row and then
			scrolling in IE with fixed virtualization does not retain the selection */
			/* trigger event so that features such as selection can apply the selection */
			this._trigger("virtualrecordsrender", null, {
				owner: this,
				tbody: this.element.children("tbody"),
				dom: this._virtualDom,
				isBuiltVirtualDom: !noVirtualDom
			});
		},
		_renderVirtualRecordsFixedInternal: function (isFixed) {
			var i, j, row, c, headerText, vheaderNode, trs,
				iRow, tr, ds = this.dataSource, data = ds.dataView(), key = this.options.primaryKey,
				vd = this._getVirtualDom(isFixed), col,
				noVirtualDom = (vd === null || vd === undefined),
				visibleCols = this._visibleColumns(isFixed);
			if (this._startRowIndex === undefined || this._startRowIndex === null) {
				this._startRowIndex = 0;
			}
			if (this._startColIndex === undefined || this._startColIndex === null) {
				this._startColIndex = 0;
			}
			if (noVirtualDom) {
				/* M.H. 5 Mar 2015 Fix for bug 189914: Hiding a column will
				result in wrong virtual data chunk rendered in the grid */
				/* M.H. 10 Mar 2016 Fix for bug 214941: When fixed virtualization and
				ColumnFixing are enabled navigating with Tab between editable cells
				throws an error when Tabbing goes outside of the loaded chunk */
				if (!this._persistVirtualScrollTop && !this._startRowIndex) {
					this._scrollTo(0, true);
				}
				this._buildVirtualDom();
				if (this._startRowIndex) {
					this._updateVirtColCounters(isFixed);
				}
				vd = this._getVirtualDom(isFixed);
				if (this.options.adjustVirtualHeights === true) {
					this._adjustVirtualHeights();
				}
			}
			/* M.H. 10 Mar 2016 Fix for bug 214941: When fixed virtualization and
			ColumnFixing are enabled navigating with Tab between editable cells
			throws an error when Tabbing goes outside of the loaded chunk */
			if (!noVirtualDom || (noVirtualDom && this._startRowIndex > 0)) {
				if (this._virtualColumnCount) {
					//this._adjustVirtualHeights();
					for (i = 0; i < this._virtualRowCount &&
						i < this._totalRowCount &&
						this._startRowIndex + i < data.length; i++) {
						if ($.type(data[ i ]) === "array") {
							// K.D. January 17th, 2013 Bug #119619 The rendering should go through _renderRecord
							row = $(this._renderRecord([ data[ this._startRowIndex + i ] ], i, isFixed));
						} else {
							// K.D. January 17th, 2013 Bug #119619 The rendering should go through _renderRecord
							row = $(this._renderRecord(data[ this._startRowIndex + i ], i, isFixed));
						}
						/* loop through cells */
						c = row.children();
						/*for (j = 0; j < c.length; j++) { */
						for (j = 0; j < this._virtualColumnCount; j++) {
							vd[ i ][ j ].innerHTML = c[ j + this._startColIndex ].innerHTML;
							/* reset classes */
							/* $(this._virtualDom[i][j]).attr('class', ''); */
							vd[ i ][ j ].className = c[ j + this._startColIndex ].className;
							vd[ i ][ j ].setAttribute("aria-describedby",
								c[ j + this._startColIndex ].getAttribute("aria-describedby"));
							vd[ i ][ j ].setAttribute("aria-readonly",
								c[ j + this._startColIndex ].getAttribute("aria-readonly"));
						}
						/* fix for data-id */
						iRow = this._startRowIndex + i;
						tr = vd[ i ][ 0 ].parentNode;
						tr.className = ((i % 2 === 0 && this.options.alternateRowStyles) ?
							"ui-iggrid-virtualrow ui-ig-altrecord" : "ui-iggrid-virtualrow");
						if (data[ iRow ]) {
							/*jscs:disable*/
							if (!_aNull(key)) {
								tr.setAttribute("data-id", this._kval_from_key(key, data[ iRow ]));
							} else if (!_aNull(data[ iRow ].ig_pk)) {
								tr.setAttribute("data-id", data[ iRow ].ig_pk);
							}
							/*jscs:enable*/
						}
					}
				} else if (isFixed) {
					trs = this.fixedBodyContainer().find("tbody>tr");
					for (i = 0; i < this._virtualRowCount &&
						i < this._totalRowCount &&
						this._startRowIndex + i < data.length; i++) {
						iRow = this._startRowIndex + i;
						tr = trs.eq(i)[ 0 ];
						if (_aNull(tr)) {
							continue;
						}
						tr.className = ((i % 2 === 0 && this.options.alternateRowStyles) ?
							"ui-iggrid-virtualrow ui-ig-altrecord" : "ui-iggrid-virtualrow");
						if (data[ iRow ]) {
							/*jscs:disable*/
							if (!_aNull(key)) {
								tr.setAttribute("data-id", this._kval_from_key(key, data[ iRow ]));
							} else if (!_aNull(data[ iRow ].ig_pk)) {
								tr.setAttribute("data-id", data[ iRow ].ig_pk);
							}
							/*jscs:enable*/
						}
					}
				}
			}
			/* make sure to update the headers , too. */
			if (this._isHorizontal) {
				this._isHorizontal = false;
				if ((this.options.virtualization === true ||
					this.options.columnVirtualization === true) &&
					parseInt(this.options.width, 10) > 0) {
					if (!this._vheaders) {
						// M.H. 10 June 2015 Fix for bug 201098: When column virtualization and sorting are enabled scrolling horizontally shows the headers wrong
						// M.H. 12 June 2015 Fix for bug 201174: With column virtualization enabled hiding and then showing a column will result in wrong header text
						this._vheaders = this.headersTable().find("thead > tr > th > span.ui-iggrid-headertext");
					}
					for (i = 0; i < visibleCols.length; i++) {
						col = visibleCols[ i ];
						if (col.headerCssClass) {
							this.headersTable().find("th." + col.headerCssClass).removeClass(col.headerCssClass);
						}
					}
					for (j = 0; j < this._virtualColumnCount; j++) {
						/* L.A. 21 March 2012 Fixed bug 97377 - When using virtualization and the grid is
						scrolled horizontally, the IDs of the column header cells are not updated accordingly */
						headerText = visibleCols[ j + this._startColIndex ].headerText;
						vheaderNode = $(this._vheaders[ j ]);
						/* M.H. 3 Sep 2013 Fix for bug #146741: When column virtualization is used with
						Sorting scrolling horizontally doesn't update the IDs of the column header cells */
						vheaderNode.closest("th")
							.attr("id", this.element[ 0 ].id + "_" + visibleCols[ j + this._startColIndex ].key);
						vheaderNode.parent().addClass(visibleCols[ j + this._startColIndex ].headerCssClass || "");
						vheaderNode.html(headerText);
					}
				}
			}
		},
		_updateVirtColCounters: function (isFixed) {
			var visibleColsLength = this._visibleColumns(isFixed).length;
			if (this.options.columns) {
				this._totalColumnCount = visibleColsLength;
				this._maxVirtualColumnCount = this.options.columns.length;
			} else {
				this._totalColumnCount = this.dataSource.dataView()[ 0 ].length;
				this._maxVirtualColumnCount = this._totalColumnCount;
			}
			if (this.options.columnVirtualization === false && this.options.virtualization === false) {
				this._virtualColumnCount = visibleColsLength;
			} else {
				if (this.options.width === null) {
					this._virtualColumnCount = this._totalColumnCount;
				} else {
					this._virtualColumnCount =
						Math.ceil(parseInt(this.options.width, 10) / this._avgColumnWidth());
					this._maxVirtualColumnCount =
						Math.min(this._maxVirtualColumnCount, this._virtualColumnCount);
					this._virtualColumnCount =
						Math.min(this._totalColumnCount, this._virtualColumnCount);
					/* update the horizontal scrollbar inner width (div) */
					this._vhorizontalcontainer()
						.children()
						.first()
						.css("width", this._calculateContainerWidth(false));
				}
			}
			/* M.H. 7 Mar 2016 Fix for bug 215052: Hiding a column with continuous
			virtualization causes row editing to enter edit mode only for the first column */
			/* igGridUpdating expects this.grid._virtualColumnCount
			to be undefined when virtualization mode is continuous */
			if (this.options.virtualizationMode === "continuous") {
				this._virtualColumnCount = undefined;
			}
		},
		_getVirtualDom: function (isFixed) {
			if (!isFixed) {
				return this._virtualDom;
			}
			return this._fixedVirtualDom;
		},
		_resetVirtualDom: function () {
			this._virtualDom = null;
			this._fixedVirtualDom = null;
		},
		_buildVirtualDom: function () {
			// M.H. 2 Sep 2014 Fix for bug #178964: When there is a fixed column and virtualizationMode is "fixed" scrolling the grid increases fixed area's height.
			var fixed, trs, fixedTrs;
			if (this.hasFixedColumns()) {
				fixedTrs = this._buildVirtualDomInternal(true);
				fixed = false;
			}
			trs = this._buildVirtualDomInternal(fixed);
			this._trigger("virtualdombuilt", null, { rows: trs, fixedRows: fixedTrs });
		},
		_buildVirtualDomInternal: function (isFixed) {
			var grid = this, markup = "", row, i, j, dataLinkFn, vdh, $vcont,
				/*ar = this.options.accessibilityRendering, */
				shouldHide = false, $tbody, visibleCols = this._visibleColumns(isFixed),
				key = this.options.primaryKey, data = this.dataSource.dataView(), temp,
				vd = this._getVirtualDom(isFixed), startIndex = 0;
			if (this._startRowIndex === undefined || this._startRowIndex === null) {
				this._startRowIndex = 0;
				this._startColIndex = 0;
			}
			vd = [];
			if (!isFixed) {
				this._virtualDom = vd;
			} else {
				this._fixedVirtualDom = vd;
			}
			this._totalRowCount = data.length;
			if (this.options.height === null) {
				this._virtualRowCount = this._totalRowCount;
			} else {
				$vcont = this._vdisplaycontainer();
				vdh = $vcont.innerHeight();
				/* M.H. 25 May 2016 Fix for bug 219580: Rows height fills the whole grid height when fixed virtualization is enabled and autoAdjustHeight is set to true after sorting*/
				if (!vdh && $vcont.is(":visible")) {
					this._virtualHeightReset = true;
					this._initializeHeights();
					this.element.height("");
					vdh = $vcont.innerHeight();
				}
				this._virtualRowCount = Math.floor(vdh /
										parseInt(this.options.avgRowHeight, 10));
			}
			this._updateVirtColCounters(isFixed);
			if (this.options.height !== null) {
				//$('#' + this.element[0].id + '_scrollContainer').children().first().height(this._totalRowCount * parseInt(this.options.avgRowHeight, 10));
				this._setScrollContainerHeight(this._totalRowCount *
					parseInt(this.options.avgRowHeight, 10));
			}
			dataLinkFn = function (row, i, vc) {
				//$(row).children().each(function (col) {
				//$(this).data('row', i).data('col', col);
				// link references
				//for (j = grid._startColIndex; j < vc + grid._startColIndex; j++) {
				for (j = 0; j < vc; j++) {
					if (j < grid._totalColumnCount) {
						vd[ i ][ j ] = row[ 0 ].cells[ j ];
					}
				}
			};

			if (this._persistVirtualScrollTop) {
				// M.K. Preserve scroll position after dataBind
				if (this._startRowIndex > this.dataSource.dataView().length - this._virtualRowCount) {
					// data source has changed to one with smaller size
					if (this.dataSource.dataView().length - this._virtualRowCount > 0) {
						this._startRowIndex = this.dataSource.dataView().length - this._virtualRowCount;
					} else {
						this._startRowIndex = 0;
					}
				}
				startIndex = this._startRowIndex;
			}
			if (isFixed) {
				this.element.children("tbody").empty();
				$tbody = this.fixedBodyContainer().find("tbody");
			} else {
				$tbody = this.element.children("tbody");
			}
			$tbody.empty();
			for (i = 0; i < this._virtualRowCount; i++) {
				shouldHide = false;
				vd[ i ] = [];
				markup = "";
				for (j = this._startColIndex; j < this._virtualColumnCount + this._startColIndex; j++) {
					if (j >= this._totalColumnCount) {
						break;
					}
					/* if (ar) { */
					markup += "<td role=\"gridcell\" aria-readonly=\"" + !!visibleCols[ j ].readOnly +
						"\" aria-describedby=\"" + this.id() + "_" + visibleCols[ j ].key +
						"\" tabindex=\"" + this.options.tabIndex + "\"";
					/* } else {
						markup += '<td';
					} */
					if (!data[ startIndex + i ]) {
						shouldHide = true;
						/* L.A. 09 November 2012 - Fixing bug #128605 */
						/* Tabbing within 'Add New Row' section , while no rows exist, is causing editRowEnding event to fire earlier */
						markup += "></td>";
					} else {
						if (this.options.autoFormat !== false) {
							if (visibleCols[ j ].template && visibleCols[ j ].template.length) {
								temp = this._renderTemplatedCell(data[ startIndex + i ], visibleCols[ j ]);
								if (temp.indexOf("<td") === 0) {
									markup += temp.substring(3);
								} else {
									markup += ">" + temp;
								}
								markup = grid._editCellStyle(markup, data[ startIndex + i ], visibleCols[ j ].key || j);
							} else {
								markup += grid._addCellStyle(
									data[ startIndex + i ],
									visibleCols[ j ].key || j,
									visibleCols[ j ]) + ">" + this._renderCell(data[ startIndex + i ][ visibleCols[ j ].key ],
									visibleCols[ j ],
									data[ startIndex + i ]
								);
							}
						} else {
							markup += grid._addCellStyle(
								data[ startIndex + i ],
								visibleCols[ j ].key,
								visibleCols[ j ]
							) + ">" + data[ i ][ visibleCols[ j ].key ];
						}
						markup += "</td>";
					}
				}
				row = $("<tr>" + markup + "</tr>").appendTo($tbody);
				if (shouldHide) {
					row.css("visibility", "hidden");
				}
				if (i % 2 === 0 && this.options.alternateRowStyles) {
					row.addClass("ui-ig-altrecord");
				}
				row.addClass("ui-iggrid-virtualrow");
				if (i % 2 === 0 && this.options.alternateRowStyles) {
					row.addClass("ui-ig-altrecord");
				}
				/* if (ar) { */
				row.attr("role", "row");
				row.attr("tabindex", this.options.tabIndex);
				/* } */
				if (data[ i + startIndex ]) {
					/*jscs:disable*/
					if (!_aNull(key)) {
						row.attr("data-id", this._kval_from_key(key, data[ i + startIndex ]));
					} else if (!_aNull(data[ i + startIndex ].ig_pk)) {
						row.attr("data-id", data[ i + startIndex ].ig_pk);
					}
					/*jscs:enable*/
				}
				dataLinkFn(row, i, this._virtualColumnCount);
				/* K.D. April 2nd, 2012 Bug #107070 "flag is not defined" error
				is thrown in jquery.tmlp.js when rowVirtualization=true */
				/* Breaking out of the loop if the dataView is smaller than the virtual column collection. */
				if (i === data.length - 1) {
					break;
				}
			}
			this._updateVirtualScrollContainer();
			return $tbody.children("tr");
		},
		_adjustVirtualHeights: function () {
			var c = this._vdisplaycontainer(),
				tbody = c.find("tbody"),
				h = tbody.children().first().height();
			if (this.options.height === null) {
				return;
			}
			/* 1. adjust avgRowHeight */
			if (this.options.avgRowHeight !== h) {
				this.options.avgRowHeight = h;
				/* adjust the first child of the scroll container */
				/* $('#' + this.element[0].id + '_scrollContainer').children().first().height(this._totalRecordsCount * this.options.avgRowHeight); */
				/* recalc */
				/* this._virtualRowCount = parseInt(this.options.height, 10) / parseInt(this.options.avgRowHeight, 10); */
				this._buildVirtualDom();
			}
		},
		_verticalScroller: function () {
			if (!this._verticalScrollerObj || this._verticalScrollerObj.length === 0) {
				this._verticalScrollerObj = this._scrollContainer().children().first();
			}
			return this._verticalScrollerObj;
		},
		_hscrollbar: function () {
			return this.container().find("#" + this.id() + "_hscroller_container");
		},
		_hscrollbarcontent: function () {
			return this.container().find("#" + this.id() + "_hscroller");
		},
		_hscrollbarinner: function () {
			return this.container().find("#" + this.id() + "_hscroller_inner");
		},
		/* calculates the average column width. This is needed for
		horizontal virtual scrolling where we need to determine */
		/* the visible virtual columns */
		_avgColumnWidth: function () {
			var width = 0, cols = this.options.columns,
				count = cols.length,
				def = this.options.defaultColumnWidth, i;
			if (this.options.avgColumnWidth !== null) {
				return parseInt(this.options.avgColumnWidth, 10);
			}
			for (i = 0; i < count; i++) {
				width += parseInt(cols[ i ].width, 10);
			}
			if ((count === 0 || isNaN(width)) && def) {
				return parseInt(def, 10);
			}
			if (count > 0 && this.options.autoGenerateColumns && isNaN(width)) {
				if (this.options.width === null || this.options.width === undefined) {
					throw new Error($.ig.Grid.locale.columnVirtualizationRequiresWidth);
				}
				return parseInt(this.options.width, 10) / count;
			}
			if (width === 0 || (width < def)) {
				return def;
			}
			/* M.H. 24 Nov 2014 Fix for bug #185557: Width=100% setting is
			ignored for grid headers if virtualization is on. */
			if (isNaN(width)) {
				width = 100;
			}
			return width / count;
		},
		/* Sync scroll containers */
		_registerScrllCntnrToSync: function ($container) {
			// adds $container(jQuery representation of the container) in internal collection
			// used to synchronize scrollLeft of the containers
			// NOTE: each $container should have stored its unique "containerName" in its arbitary data - taken via $.data
			var name = $container.data("containerName");
			/* collection of scroll containers that should be synced their scrollLeft position */
			this._scrllCntrsToSync = this._scrllCntrsToSync || {};
			this._scrllCntrsToSync[ name ] = $container;
		},
		_syncScrollLeft: function (scrLeft, ignoreContainer) {
			// Synchronize scrollLeft position of the containers stored in this._scrllCntrsToSync
			//scrLeft - scrollLeft of the containers, ignoreContainer - name of the container that is already synced
			var name, $container, containers = this._scrllCntrsToSync;
			for (name in containers) {
				if (containers.hasOwnProperty(name)) {
					if (name === ignoreContainer) {
						continue;
					}
					$container = containers[ name ];
					$container.scrollLeft(scrLeft);
				}
			}
		},
		_onScrollContainer: function (event) {
			var $container = $(event.target), containerName, $hScrollBar,
				scrLeft = $container.scrollLeft(),
				scrTop = $(event.target).scrollTop();
				containerName = $container.data("containerName");
			if (scrLeft !== this._scrollLeft) {
				this._syncScrollLeft(scrLeft, containerName);
				/* M.H. 3 Sep 2015 Fix for bug 205538: Focusing the last cell when there are
				horizontal and vertical scroll bars is causing grid misalignment in IE the issue
				could be reproduced ONLY in IE - if TAB in the last cell it is possible to
				misalign data container with other containers(header/footer) if event is not
				fired from the scrollbar AND scrollLeft of the dataContainer is not equal to
				scrollLeft of the horizontal scrollbar - sync horizontal position of all
				containers(according to the horizontal scrollbar) */
				if ($.ig.util.isIE &&
					containerName !== "hScrollbar" &&
					containerName !== "vScrollbar") {
					$hScrollBar = this._scrllCntrsToSync.vScrollbar || this._scrllCntrsToSync.hScrollbar;
					if ($hScrollBar && $hScrollBar.scrollLeft() !== scrLeft) {
						this._synchronizeHScroll();
						return;
					}
				}
				this._scrollLeft = scrLeft;
			}
			if (this._oldDisplayContainerScrollTop === undefined) {
				this._oldDisplayContainerScrollTop = 0;
			}
			if (scrTop !== this._oldDisplayContainerScrollTop && containerName === "dataContainer") {
				if ($container[ 0 ].scrollHeight - scrTop === $container.outerHeight()) {
					//display container was scrolled to the bottom
					this._focusDataRowIndex = this._getTotalRowsCount() - 1;
					this._scrollContainer().scrollTop(this._scrollContainer().children().first().outerHeight());
				} else if (scrTop === 0) {
					//display container was scrolled to the top
					this._focusDataRowIndex = 0;
					this._scrollContainer().scrollTop(0);
					$hScrollBar = this._scrllCntrsToSync.vScrollbar || this._scrllCntrsToSync.hScrollbar;
					if ($hScrollBar) {
						$hScrollBar.scrollLeft(0);
					}
				}
			}
		},
		/* Sync scroll containers */
		_renderCaption: function () {
			var colgroup, caption, tbl, scrollc;
			if (this.options.caption !== null) {
				if (this.container().find(".ui-iggrid-headercaption").length > 0) {
					return;
				}
				if (this.options.fixedHeaders === true && this.options.showHeader === true) {
					colgroup = this.headersTable().children("colgroup").first();
					if (colgroup.length === 0) {
						caption = $("<caption></caption>").prependTo(this.headersTable());
					} else {
						//A.Y. Bug 91574. The colgroup should always be the first element of the table.
						//If we already have a colgroup in the table we need to render the caption after it.
						caption = $("<caption></caption>").insertAfter(colgroup);
					}
					caption.text(this.options.caption)
						.attr("id", this.id() + "_caption")
						.addClass(this.css.gridHeaderCaptionClass);
				} else {
					// M.H. 30 Jan 2014 Fix for bug #116158: Caption property cannot be set dynamically at runtime.
					// it should be able to set properly caption even if paging header DOM element is rendered
					scrollc = this.scrollContainer();
					if (scrollc.length === 0) {
						tbl = $("<table></table>").prependTo(this.container());
					} else {
						tbl = $("<table></table>").insertBefore(scrollc);
					}
					/* we need to render the caption in a separate table, which will
					be a first child of grid"s container. otherwise won"t stick. also if
					there are no headers, we need to have some table to put the caption in :) */
					caption = $("<caption></caption>")
						.appendTo(tbl.css("width", "100%")
						.addClass(this.css.captionTable))
						.text(this.options.caption)
						.attr("id", this.id() + "_caption")
						.addClass(this.css.gridHeaderCaptionClass);
				}
				caption.parent().css("margin", 0);
			}
		},
		/* M.H. 22 Feb 2013 Fix for bug #133539: When the grid has vertical
		scrollbar and caption fixing a column makes the headers misaligned. */
		_renderFixedCaption: function () {
			var colgroup, caption;
			if (this.options.caption !== null) {
				if (this.options.fixedHeaders === true && this.options.showHeader === true) {
					colgroup = this.fixedHeadersTable().children("colgroup").first();
					if (colgroup.length === 0) {
						caption = $("<caption></caption>").prependTo(this.fixedHeadersTable());
					} else {
						caption = $("<caption></caption>").insertAfter(colgroup);
					}
					caption.css("white-space", "nowrap")
						.text(this.options.caption).attr("id", this.id() + "_caption_fixed")
						.addClass(this.css.gridHeaderCaptionClass);
				}
				caption.parent().css("margin", 0);
			}
		},
		_createHeaderColumnMarkup: function (column, index) {
			var headerClass = this.css.headerClass,
				customClass = column.headerCssClass ? column.headerCssClass : "",
				/* ar = this.options.accessibilityRendering, */
				headerText = $("<span>" + column.headerText + "</span>").addClass(this.css.headerTextClass),
				markup = "<th></th>";
			/*if (ar) {
			headerCell = $(markup).append(headerText).attr("id", this.id() + "_" + column.key).attr("role", "columnheader").addClass(headerClass).addClass(customClass).data("columnIndex", index);
			} else {
			headerCell = $(markup).append(headerText).attr("id", this.id() + "_" + column.key).addClass(headerClass).addClass(customClass).data("columnIndex", index);
			} */
			return $(markup).append(headerText).attr({
				"id": this.id() + "_" + column.key,
				"role": "columnheader",
				"aria-label": column.headerText,
				"tabIndex": this.options.tabIndex
			}).addClass(headerClass).addClass(customClass).data("columnIndex", index);
		},
		_updateHeaderColumnIndexes: function () {
			var grid = this,
				cols = this.options.columns;
			if ((this.options.virtualization === true && this.options.virtualizationMode === "fixed") ||
					this.options.columnVirtualization === true) {
				cols = cols.slice(0, this._maxVirtualColumnCount);
			}
			/* in case of initially fixed columns and initially
			hidden columns _initialHiddenCells should not be cleared */
			grid._initialHiddenCells = this._initialized ? [] : grid._initialHiddenCells;
			$(cols).each(function (i) {
				grid.container().find("#" + grid.id() + "_" + this.key).data("columnIndex", i);
			});
		},
		_renderHeaderColumns: function (header) {
			var grid = this,
				cols = this.options.columns;
			/* if we use virtualization we need headers just for the maximum number of visible columns */
			/* I.I. bug fix for 107686 */
			if ((this.options.virtualization === true &&
				this.options.virtualizationMode === "fixed") ||
				this.options.columnVirtualization === true) {
				cols = cols.slice(0, this._maxVirtualColumnCount);
			}
			grid._initialHiddenCells = [];
			/* render header with the column names , if they are not defined, leave it empty */
			$(cols).each(function (i) {
				var headerCell, key = this.key;
				if (key) {
					headerCell = grid._createHeaderColumnMarkup(this, i);
					header.append(headerCell);
					grid._trigger(grid.events.headerCellRendered, null, {
						owner: grid,
						th: headerCell,
						columnKey: this.key
					});
					/* M.H. 10 July 2012 Fix for bug #113972 - set first to display none */
					$.each(grid._initialHiddenColumns, function () {
						if (this.key === key) {
							grid._initialHiddenCells.push(headerCell);
							headerCell.css("display", "none");
							return false;
						}
					});
				}
			});
		},
		renderMultiColumnHeader: function (cols) {
			/*
				paramType="array" an array of column objects
				When called the method re-renders the whole grid(also rebinds to the data source) and renders the cols object
			*/
			/* M.H. 31 Oct 2012 Fix for bug #125907 */
			var topmostGrid, layout;
			this._clearPersistenceData();
			this.destroy(true);
			this.options.columns = cols;
			/*clear internal variables*/
			this._container = null;
			this._rContainer = null;
			this._prevContainerHeight = null;
			this._virtualHeightReset = null;
			this._scrollContainerObj = null;
			this._initialized = false;
			/* M.H. 22 Aug 2016 Fix for bug 223901: Multi-Row Layout is not refreshed when setting new columns definition */
			this._rlm = undefined;
			this._mrl = undefined;
			this._rlp = undefined;
			this.options.requiresDataBinding = true;
			/* M.H. 5 Sep 2012 Fix for bug #120472 */
			this._renderHeaderCalled = false;
			this.persistenceData = {};
			/* M.H. 12 Nov 2012 Fix for bug #126991 */
			if (this._isHierarchicalGrid) {
				topmostGrid = this.element.closest(".ui-iggrid-root").data("igHierarchicalGrid");
				if (topmostGrid) {
					/* M.H. 10 Dec 2012 Fix for bug #126991 */
					/* M.H. 27 Sep 2013 Fix for bug #153474: The hierarchical grid throws
					a js error when you call renderMultiColumnHeader method. topmostGrid
					hasn"t id function and also this function is called only by user when
					the grid is defined and the element should be rendered */
					if (topmostGrid.element[ 0 ].id !== this.element[ 0 ].id && this.options.key) {
						layout = topmostGrid._findLayout(topmostGrid.options.columnLayouts, this.options.key);
						layout.columns = cols;
						topmostGrid._setOption("columnLayouts", topmostGrid.options.columnLayouts);
					}
					topmostGrid.persistenceData = {};
					topmostGrid._clearVariablesAndEvents();
					topmostGrid._create();
					return;
				}
			}
			this._create();
		},
		_renderHeaderLayout: function (thead) {
			var i, j, t = this._rlp, l, cell, hrow, col, headerCell;
			thead.empty();
			for (i = 0; i < t.length; i++) {
				l = t[ i ];
				hrow = $("<tr></tr>")
					.attr("data-header-row", true)
					.attr("role", "row")
					.appendTo(thead);
				for (j = 0; j < l.length; j++) {
					cell = l[ j ];
					/* optimize for subsequent calls */
					if (cell.col) {
						col = cell.col;
					} else {
						col = this.columnByKey(cell.key);
						cell.col = col;
					}
					headerCell = this._createHeaderColumnMarkup(col, i * j + j)
						.attr("colspan", cell.cs)
						.attr("rowspan", cell.rs);
					hrow.append(headerCell);
					this._trigger(this.events.headerCellRendered, null, {
						owner: this,
						th: headerCell,
						columnKey: col.key
					});
				}
			}
		},
		_renderHeader: function () {
			/*
				Event fires headerRendering and headerRendered event ONLY when showHeader is TRUE
				It is fired headerRenderedInternal event so it is used by the hierarchical grid - it should not be called headerRendered event because header should not be rendered
				but should be called headerRenderedInternal so the hierarchical grid to create expander column
			*/
			var w, header, id, headerDom, headerMarkup = "",
				scrollc = this.scrollContainer(),
				noCancel = true, fHeader = false,
				headerParent = this.element[ 0 ], width,
				headerScrollDiv,
				existingHeader,
				o = this.options,
				/*ar = o.accessibilityRendering, */
				v = o.virtualization === true ||
				o.rowVirtualization === true ||
				o.columnVirtualization === true;
			/* M.H. 10 May 2012 Fix for bug #108221 - it should be called renderHeader ONLY one time and we use this flag */
			this._renderHeaderCalled = true;
			/* M.H. 10 May 2012 Fix for bug #108221 */
			if (this.options.showHeader) {
				noCancel = this._trigger(this.events.headerRendering, null, { owner: this });
				if (noCancel) {
					this._rmvClgrpOnInitRenderData = true;
					id = this.id();
					/* in order to have fixed headers, we must have a height set at least */
					/* M.H. 14 Feb 2013 Fix for bug #132840 */
					headerMarkup = "<thead role=\"rowgroup\"><tr data-header-row role=\"row\"></tr></thead>";
					headerParent = this.container();
					width = scrollc.css("width");
					if (this.options.fixedHeaders && this.options.height !== null) {
						// set header parent
						if (v === false) {
							if (headerParent.length === 0) {
								scrollc.removeClass(this.css.gridClasses)
									.removeClass(this.css.baseClasses)
									.css("width", "")
									.wrap("<div id='" + id + "_container'></div>");
								headerParent = this.container()
									.addClass(this.css.baseClasses)
									.addClass(this.css.gridClasses)
									.css("width", width);
								this._isWrapped = true;
							}
						} else {
							// virtualization
							// I.I. bug fix for 103237
							width = this.element.parents(".ui-widget").css("width");
							headerParent = this.container().find("#" + id + "_headers_v");
							/* M.H. 24 Nov 2014 Fix for bug #185557: Width=100% setting is
							ignored for grid headers if virtualization is on. set width of
							the header only when options.width is set */
							if (o.width !== null) {
								/* M.H. 21 Mar 2014 Fix for bug #159951: When virtualization is
								enabled in a grid with % width (not 100%), the layout is broken */
								w = o.width;
								w = (w.indexOf && w.indexOf("%") !== -1) ? "100%" : w;
								headerParent.css("width", w).css("max-width", w).css("overflow-x", "hidden");
							}
						}
						headerParent = $("<table id=\"" + id + "_headers\"></table>")
							.prependTo(headerParent)
							.addClass(this._isMultiRowGrid() ?
							this.css.mrlGridHeaderTableClass : this.css.gridHeaderTableClass)
							.attr("cellpadding", 0)
							.attr("cellspacing", 0)
							.attr("border", "0");
						if (v === false) {
							// the fixed headers table should be inside another scrolling div
							headerScrollDiv = $("<div></div>").prependTo(headerParent.parent());
							headerScrollDiv
								.css("overflow", "hidden")
								.css("position", "relative")
								.css("white-space", "nowrap");
							headerScrollDiv.addClass("ui-widget-header ui-helper-reset");
							headerScrollDiv.append(headerParent);
						} else if (this.options.rowVirtualization === true) {
							headerParent.addClass("ui-widget-header ui-helper-reset");
						}
						/*M.K. 2/3/2015 Fix for bug 187852: When navigating using Tab key on
						grid columns and there is horizontal scrollbar, the scroll affects the
						column header attach handler on the grid header scrolling container */
						this.headersTable().parent()
							.data("containerName", "headerContainer")
							.bind("scroll.syncScrollContainers", $.proxy(this._onScrollContainer, this));
						this._registerScrllCntnrToSync(this.headersTable().parent());
						fHeader = true;
						/* M.H. 15 July 2015 Fix for bug 202621: Columns set with 0% width are
						not hidden while loading data In case of hierarchical grid with fixedHeaders
						is false then on call _headerrenderedinternal it is NOT rendered data-skip expander col in colgroup */
						this._rmvClgrpOnInitRenderData = false;
					} else if (this.options.fixedHeaders !== true) {
						headerParent = this.element;
					}
					/* no scrolling */
					if (this.options.width === null && this.options.height === null) {
						headerParent = this.element;
					}
					if (this.options.width !== null && this.options.height === null) {
						/* OK 13 OCT 2011 Fixed <91075> - When grid has only one
						row buttons Done,Cancel and Delete are not vissible */
						this._fixScrollY = 1;
						scrollc.css("overflow-y", "hidden").css("overflow-x", "auto");
						headerParent = this.element;
					}
					/* M.H. 10 July 2015 Fix for bug 202621: Columns set
					with 0% width are not hidden while loading data */
					/* when grid data is populated from remote data source - first renderGrid
					is called and after that(async) is called renderData. There are cases when
					colgroup is not rendered(e.g. in case of height is not set) and it is possible
					columns in grid header to be with inappropriate widths(because of renderColgroup
					is not called in all cases in function _renderGrid) */
					if (!headerParent.find("colgroup").length) {
						this._renderColgroup(headerParent, fHeader, false, this.options.autofitLastColumn);
					}
					if (this._isMultiColumnGrid === true) {
						// it the grid has multicolumn headers then fire internal event. MulticolumnHeader widget is bound to this event and implements the logic for rendering
						this._headerParent = headerParent;
						this._fireInternalEvent("_renderingMultiColumnHeader");
						/* M.H. 30 July 2012 Fix for bug #117466 */
						if (this.options.virtualization === true || this.options.rowVirtualization === true) {
							/* append headers to the topmost table, so that they are fixed */
							this.container().find("#" + id + "_headers_v")
								.css("position", "relative")
								/* apply header styles as the div is now padded instead of the table */
								.addClass("ui-widget-header ui-helper-reset")
								.addClass(this.css.baseClasses);
							headerParent.addClass(this._isMultiRowGrid() ?
								this.css.mrlGridHeaderTableClass : this.css.gridHeaderTableClass)
								.attr("cellpadding", 0)
								.attr("cellspacing", 0)
								.attr("border", "0");
						}
					} else {
						if (this.options.headerTemplate === null || this.options.headerTemplate === undefined) {
							if (this.options.virtualization === true || this.options.rowVirtualization === true) {
								//append headers to the topmost table, so that they are fixed
								this.container().find("#" + id + "_headers_v")
									.css("position", "relative")
									/* apply header styles as the div is now padded instead of the table */
									.addClass("ui-widget-header ui-helper-reset")
									.addClass(this.css.baseClasses);

								headerParent.addClass(this._isMultiRowGrid() ?
									this.css.mrlGridHeaderTableClass : this.css.gridHeaderTableClass)
									.attr("cellpadding", 0)
									.attr("cellspacing", 0)
									.attr("border", "0");
							}
							/* L.A. 30 November 2012 Bug #128510 - the headers are displayed twice
							if you set height to the control while using HTML table as data source */
							this.element.find(">thead").remove();
							/* L.A. 10 May 2012 Bug #110557 - check for existing header before adding a new one */
							existingHeader = headerParent.find(">thead");
							if (existingHeader.length === 0) {
								header = $(headerMarkup).appendTo(headerParent).children().first();
							} else {
								existingHeader.empty();
								/* L.A. 15 August 2012 Fixing bugs #118934, #74552 */
								/* M.H. 14 Feb 2013 Fix for bug #132840 */
								header = $("<tr data-header-row role=\"row\"></tr>").appendTo(existingHeader);
							}
							if (this._rlp) {
								this._renderHeaderLayout(header.parent());
							} else {
							this._renderHeaderColumns(header);
							}
						} else {
							// check if there is a user defined template
							// the header template DOES NOT use jQuery templating, it"s plain HTML with the actual column text there.
							headerDom = this.options.headerTemplate;
							if (this.options.virtualization === true || this.options.rowVirtualization === true) {
								$(headerDom).children().each(function () {
									this.appendTo(this.container().find("#" + id + "_headers"));
								});
								header = this.headersTable().addClass(this.css.gridClasses);
							} else {
								header = $(headerMarkup).appendTo(headerParent).append(headerDom);
							}
						}
						this._headerParent = headerParent;
						this._trigger(this.events.headerRendered, null, { owner: this, table: this.headersTable() });
						this._headerRenderCancel = false;
					}
				} else {
					this._headerRenderCancel = true;
				}
			}
			/* M.H. 10 May 2012 Fix for bug #108221 - if headerRendering is called AND is
			canceled then it should not be called internal event headerRenderedInternal */
			/* if (this._headerRenderCancel !== true) { */
			/* M.H. 7 Aug 2012 Fix for bug #118444 */
			this._trigger("headerRenderedInternal", null, { owner: this, table: this.headersTable() });
		},
		_renderFooter: function () {
			var gridId = this.id(),
				w, o = this.options,
				elemParent,
				footerId = gridId + "_footer_container",
				footerMarkup = "<tfoot role=\"rowgroup\" class=\"" + this.css.gridFooterClass + "\"></tfoot>",
				$footer = this.container().find("#" + footerId),
				$table,
				width,
				noCancel,
				tableGrid = this.element,
				/* footerParent = this.element[0], */
				$tableGridContainer;
			if (tableGrid.length === 0) {
				return;
			}
			/* check whether footer container is rendered */
			if ($footer.length === 0 && o.showFooter) {
				noCancel = this._trigger(this.events.footerRendering, null, { owner: this });
				if (noCancel) {
					// M.H. 2 April 2012 Fix for bug #107566
					if ((o.virtualization === true ||
						o.rowVirtualization === true ||
						o.columnVirtualization === true) &&
						((o.height !== null &&
						o.height !== undefined) ||
						(o.width !== null && o.width !== undefined))) {
						$tableGridContainer = this._vdisplaycontainer();
						this.options.fixedFooters = true;
						w = o.width;
						/* M.H. 21 Mar 2014 Fix for bug #159951: When virtualization is
						enabled in a grid with % width (not 100%), the layout is broken */
						if (w && w.indexOf && w.indexOf("%") !== -1) {
							w = "100%";
						}
						$tableGridContainer = $("<tr><td colspan=\"2\" style=\"border-width: 0px;\"></td></tr>")
							.insertAfter($tableGridContainer.closest("tr"));
						/* $footer = $("#" + footerId).find("tfoot"); */
						$footer = $("<div></div>")
							.attr("id", footerId)
							.css({ "overflow": "hidden", "position": "relative" })
							.addClass("ui-widget-footer")
							.addClass(this.css.footerContainer)
							.width(w)
							.appendTo($tableGridContainer.find("td"));
						$table = $("<table></table>")
							.attr("cellpadding", 0)
							.attr("cellspacing", 0)
							.attr("border", 0)
							.appendTo($footer)
							.addClass(this.css.gridFooterTableClass)
							.width(w)
							.attr("id", gridId + "_footers");
						this._renderColgroup($table, false, true, this.options.autofitLastColumn);
						$(footerMarkup).appendTo($table);
						/* M.H. 10 April 2012 Fix for bug #108453 */
						if (this.options.showFooter &&
							this.options.fixedFooters === true &&
							this.options.height !== null) {
							elemParent = this.element.parents(".ui-widget:first");
							if (elemParent.length === 1 && elemParent[ 0 ].style) {
								width = elemParent[ 0 ].style.width;
								/* M.H. 4 Apr 2014 Fix for bug #159951: When virtualization
								is enabled in a grid with % width (not 100%), the layout is broken */
								if (width && (!width.indexOf || width.indexOf("%") === -1)) {
									$footer.css("width", width);
								}
							}
						}
						/* M.H. 11 April 2012 Fix for bug #108438 */
						if (o.expandColWidth && !o.width) {
							$footer.css("width", (parseInt($footer.width(), 10) + o.expandColWidth) + "px");
						}
					} else if (o.fixedFooters === true && o.height !== null) {
						if (o.width !== null) {
							$tableGridContainer = this._hscrollbar();
						} else {
							$tableGridContainer = this.scrollContainer();
						}
						/* usually table is wrapped in scroll element - e.g. in case when width/height are set
						when they are not set then tableGridContainer should be the table */
						if ($tableGridContainer.length === 0) {
							$tableGridContainer = tableGrid;
						}
						$footer = $("<div></div>")
							.attr("id", footerId)
							.css({
								"overflow": "hidden",
								"position": "relative",
								"white-space": "nowrap"
							})
							.addClass(this.css.footerContainer)
							.addClass("ui-widget-footer")
							.insertAfter($tableGridContainer);
						$table = $("<table></table>")
							.attr("cellpadding", 0)
							.attr("cellspacing", 0)
							.attr("border", 0)
							.appendTo($footer)
							.addClass(this.css.gridFooterTableClass)
							.attr("id", gridId + "_footers");
						this._renderColgroup($table, false, true, this.options.autofitLastColumn);
						$(footerMarkup).appendTo($table);
					} else {
						$tableGridContainer = tableGrid;
						/* according to W3C specification tfoot should be before tbody */
						/* M.K. 28 May 2015: Move TFOOT rendering after the TBODY (in order to support ARIA). */
						/* table grid container should be the table where is rendered grid */
						$footer = $(footerMarkup).attr("id", footerId)
							.insertAfter($tableGridContainer.find("tbody:eq(0)"));
					}
					/* M.H. 11 June 2015 Fix for bug 201082: When row virtualization is
					enabled and cell is focused pressing TAB key to move the focus the
					cells and headers get misaligned. */
					$footer
						.data("containerName", "footerContainer")
						.bind("scroll.syncScrollContainers", $.proxy(this._onScrollContainer, this));
					this._registerScrllCntnrToSync($footer);
					/* M.H. 11 Oct. 2011 Fix for bug #91147 */
					$footer.css("display", "none");
					this._footer = $footer;
					this._footerParent = $table;
					this._trigger(this.events.footerRendered, null, { owner: this, table: this.footersTable() });
				}
			}
			return $footer;
		},
		_renderRecord: function (data, rowIndex, isFixed) {
			// generate a Tr and append it to the table
			var key = this.options.primaryKey,/*ar = this.options.accessibilityRendering,*/
				gridId = this.id(),
				grid = this, dstr = "", cols = this.options.columns, noVisibleColumns, temp;
			dstr += "<tr";
			if (rowIndex % 2 !== 0 && this.options.alternateRowStyles) {
				dstr += " class=\"" + grid.css.recordAltClass + "\"";
			}
			/*jscs:disable*/
			if (!_aNull(key)) {
				dstr += " data-id=\"" + this._kval_from_key(key, data) + "\"";
			} else if (!_aNull(data.ig_pk)) {
				dstr += " data-id=\"" + data.ig_pk + "\"";
			}
			/*jscs:enable*/
			/* data index to which the row is bound */
			if (this.options.virtualization && this.options.virtualizationMode === "continuous") {
				dstr += " data-row-idx=\"" + rowIndex + "\"";
			}
			dstr += " role=\"row\" tabindex=\"" + this.options.tabIndex + "\">";
			noVisibleColumns = true;
			isFixed = !!isFixed;
			$(cols).each(function (colIndex) {
				var col = cols[ colIndex ], f = !!col.fixed;
				if (col.hidden || f !== isFixed) {
					return;
				}
				noVisibleColumns = false;
				/* if (ar) { */
				dstr += "<td role=\"gridcell\" aria-readonly=\"" + !!this.readOnly +
					"\" aria-describedby=\"" + gridId + "_" + this.key +
					"\" tabindex=\"" + grid.options.tabIndex + "\"";
				/* } else {
					dstr += '<td';
				} */
				if (col.template && col.template.length) {
					temp = grid._renderTemplatedCell(data, this);
					if (temp.indexOf("<td") === 0) {
						dstr += temp.substring(3);
					} else {
						dstr += ">" + temp;
					}
					dstr = grid._editCellStyle(dstr, data, this.key);
				} else {
					dstr += grid._addCellStyle(data, this.key || colIndex, col) +
						">" + grid._renderCell(data[ this.key || colIndex ], this, data);
				}
				dstr += "</td>";
			});
			if (noVisibleColumns && !isFixed) {
				dstr += "<td role=\"gridcell\"></td>";
			}
			dstr += "</tr>";
			return dstr;
		},
		_renderRecordFromLayout: function (data, rowIndex, isFixed) {
			var t = this._rlp, l, cell, key = this.options.primaryKey,
				dstr = "", col, noVisibleColumns, temp, i, j,
				alt = rowIndex % 2 !== 0 && this.options.alternateRowStyles;
			for (i = 0; i < t.length; i++) {
				l = t[ i ];
				/*jscs:disable*/
				dstr += "<tr" + (alt ? " class=\"" + this.css.recordAltClass + "\"" : "");
				if (!_aNull(key)) {
					dstr += " data-id=\"" + this._kval_from_key(key, data) + "\"";
				} else if (!_aNull(data.ig_pk)) {
					dstr += " data-id=\"" + data.ig_pk + "\"";
				}
				/*jscs:enable*/
				/* data index to which the row is bound */
				if (this.options.virtualization && this.options.virtualizationMode === "continuous") {
					dstr += " data-row-idx=\"" + rowIndex + "\"";
				}
				dstr += " role=\"row\" tabindex=\"" + this.options.tabIndex + "\">";
				noVisibleColumns = true;
				for (j = 0; j < l.length; j++) {
					cell = l[ j ];
					/* optimize for subsequent calls */
					if (cell.col) {
						col = cell.col;
					} else {
						col = this.columnByKey(cell.key);
						cell.col = col;
					}
					if (col.hidden || (col.fixed === true && !isFixed) || (col.fixed !== true && isFixed)) {
						return;
					}
					noVisibleColumns = false;
					dstr += "<td role=\"gridcell\" aria-readonly=\"" + !!col.readOnly + "\" " +
						(cell.cs > 0 ? "colspan=\"" + cell.cs + "\" " : "") +
						(cell.rs > 0 ? "rowspan=\"" + cell.rs + "\" " : "") +
						"aria-describedby=\"" + this.id() + "_" + col.key + "\" tabindex=\"" +
						this.options.tabIndex + "\"";
					if (col.template && col.template.length) {
						temp = this._renderTemplatedCell(data, col);
						if (temp.indexOf("<td") === 0) {
							dstr += temp.substring(3);
						} else {
							dstr += ">" + temp;
						}
						dstr = this._editCellStyle(dstr, data, col.key);
					} else {
						dstr += this._addCellStyle(data, col.key, col) + ">" +
							this._renderCell(data[ col.key ], col, data);
					}
					dstr += "</td>";
				}
				if (noVisibleColumns) {
					dstr += "<td role=\"gridcell\"></td>";
				}
				dstr += "</tr>";
			}
			return dstr;
		},
		_editCellStyle: function (dstr, data, col, isFixed) {
			var lastCellIdx, lastCellClose, lastCellClass, start, end, nc = "", i;
			if (!this._cellStyleSubscribers || this._cellStyleSubscribers.length === 0) {
				return dstr;
			}
			for (i = 0; i < this._cellStyleSubscribers.length; i++) {
				nc += this._cellStyleSubscribers[ i ](data, col, isFixed) + " ";
			}
			nc = nc.trim();
			if (nc.length === 0) {
				return dstr;
			}
			lastCellIdx = dstr.lastIndexOf("<td");
			lastCellClose = dstr.indexOf(">", lastCellIdx);
			if (lastCellClose > 0) {
				lastCellClass = dstr.substring(lastCellIdx, lastCellClose).indexOf("class");
				if (lastCellClass > 0) {
					lastCellClass += lastCellIdx;
				}
			} else {
				lastCellClass = dstr.indexOf("class", lastCellIdx);
			}
			if (lastCellClass > 0) {
				start = dstr.substring(0, lastCellClass + 7);
				end = dstr.substring(lastCellClass + 7);
				dstr = start + nc + " " + end;
			} else {
				start = dstr.substring(0, lastCellIdx + 3);
				end = dstr.substring(lastCellIdx + 3);
				dstr = start + " class=\"" + nc + "\"" + end;
			}
			return dstr;
		},
		_addCellStyle: function (data, colId, col, isFixed) {
			var dstr = "", i,
				hasSubs = this._cellStyleSubscribers && this._cellStyleSubscribers.length,
				hasColClasses = col ? col.columnCssClass : false;
			if (hasSubs) {
				for (i = 0; i < this._cellStyleSubscribers.length; i++) {
					dstr += this._cellStyleSubscribers[ i ](data, colId, isFixed) + " ";
				}
			}
			if (hasColClasses) {
				dstr += col.columnCssClass;
			}
			dstr = dstr.trim();
			return dstr.length > 0 ? " class=\"" + dstr + "\"" : dstr;
		},
		_getCellStyle: function (data, col, isFixed) {
			var dstr = "", i;
			if (!this._cellStyleSubscribers || this._cellStyleSubscribers.length === 0) {
				return dstr;
			}
			for (i = 0; i < this._cellStyleSubscribers.length; i++) {
				dstr += this._cellStyleSubscribers[ i ](data, col, isFixed) + " ";
			}
			return dstr.trim();
		},
		_enableHeaderCellFeature: function (th) {
			// apply css class to the specified header cell(or the header cell with the specified column key)
			var $th;
			if ($.type(th) === "string") {
				$th = this.container().find("#" + this.id() + "_" + th);
			} else {
				$th = th;
			}
			$th.addClass(this.css.headerCellFeatureEnabledClass);
		},
		_renderRecordInArray: function (darr, tbody, data, rowIndex) {
			// generate a Tr and append it to the table
			var key = this.options.primaryKey,
				/*ar = this.options.accessibilityRendering, */
				grid = this, appendBehavior = false,
				cols = this.options.columns,
				temp,
				tdIndex;
			if (darr === null) {
				darr = [];
				appendBehavior = true;
			}
			darr.push("<tr");
			if (rowIndex % 2 !== 0 && this.options.alternateRowStyles) {
				darr.push(" class=\"" + grid.css.recordAltClass + "\"");
			}
			/*jscs:disable*/
			if (!_aNull(key)) {
				darr.push(" data-id=\"" + this._kval_from_key(key, data) + "\"");
			} else if (!_aNull(data.ig_pk)) {
				darr.push(" data-id=\"" + data.ig_pk + "\"");
			}
			/*jscs:enable*/
			/* if (ar) { */
			darr.push(" role=\"row\" tabindex=\"" + this.options.tabIndex + "\">");
			/*} else {
				darr.push('>');
			} */
			$(cols).each(function (colIndex) {
				if (cols[ colIndex ].hidden) {
					return;
				}
				/* if (ar) { */
				darr.push("<td role=\"gridcell\" aria-readonly=" + !!this.readOnly +
					" aria-describedby=\"" + grid.id() + "_" + this.key +
					"\" tabindex=\"" + grid.options.tabIndex + "\"");
				/*} else {
				darr.push('<td ');
				} */
				/* K.D. April 17th, 2012 Bug #109497 Template and non-template rendering is now branched in here */
				if (data[ this.key ] === undefined) {
					if (cols[ colIndex ].template && cols[ colIndex ].template.length) {
						temp = grid._renderTemplatedCell(data, this);
						if (temp.indexOf("<td") === 0) {
							tdIndex = darr.length - 1;
							darr[ tdIndex ] = temp.replace("<td", darr[ tdIndex ]);
						} else {
							darr.push(">" + temp);
							tdIndex = darr.length - 2;
						}
						darr[ tdIndex ] = grid._editCellStyle(darr[ tdIndex ], data, colIndex);
					} else {
						darr.push(grid._addCellStyle(data, colIndex, cols[ colIndex ]) +
							">" + grid._renderCell(data[ colIndex ], this, data));
					}
					darr.push("</td>");
				} else {
					if (cols[ colIndex ].template && cols[ colIndex ].template.length) {
						temp = grid._renderTemplatedCell(data, this);
						if (temp.indexOf("<td") === 0) {
							tdIndex = darr.length - 1;
							darr[ tdIndex ] = temp.replace("<td", darr[ tdIndex ]);
						} else {
							darr.push(">" + temp);
							tdIndex = darr.length - 2;
						}
						darr[ tdIndex ] = grid._editCellStyle(darr[ tdIndex ], data, this.key);
					} else {
						darr.push(grid._addCellStyle(data, this.key, cols[ colIndex ]) +
							">" + grid._renderCell(data[ this.key ], this, data));
					}
					darr.push("</td>");
				}
			});
			darr.push("</tr>");
			if (appendBehavior) {
				tbody.append(darr.join(""));
			}
		},
		_fixDate: function (val, col) {
			var d, i;
			if (!col || !val) {
				return val;
			}
			if (col.dataType === "date" && !val.getTime) {
				i = val.indexOf ? val.indexOf("ate(") : -1;
				if (i > 0) {
					d = val.substring(i + 4);
					i = d.indexOf(")");
					if (i > 0) {
						d = parseInt(d.substring(0, i), 10);
						if (!isNaN(d)) {
							val = new Date(d);
						}
					}
				}
			}
			return val;
		},
		/* handles formatting */
		_renderCell: function (val, col, record, displayStyle, returnObject) {
			var type = col.dataType, format = col.format, o = this.options, auto = o.autoFormat;

			if (record && !returnObject) {
				val = this.dataSource.getCellValue(col.key, record);
			}
			val = this._fixDate(val, col);
			if (col.formatter) {
				return col.formatter(val, record);
			}
			/* K.D. August 21st, 2012 Bug #119317 The checkBox is not
			rendered when column template is used in the grid. */
			if (!format && type === "bool" && o.renderCheckboxes) {
				format = "checkbox";
			}
			if (format === "checkbox" && type !== "bool") {
				format = null;
			}
			type = (type === "date" || type === "number") ? type : "";
			if (format || ((auto === true || auto === "dateandnumber") && type) || (auto && auto === type)) {
				// K.D. August 21st, 2012 Bug #119317 The checkBox is not rendered when column template is used in the grid.
				// L.A. 17 October 2012 - Fixing bug #123215 The group rows of a grouped checkbox column are too large
				return $.ig.formatter(
					val,
					type,
					format,
					true,
					o.enableUTCDates,
					displayStyle,
					col.headerText,
					this.options.tabIndex
				);
			}
			/* L.A. 24 October 2012 - Fixing bug #125387 Boolean value
			is not evaluated correctly in rowTemplate */
			/* L.A. 12 November 2012 - Fixing bug #127006 Changing a value in an existing row causes boolean column values
			(from the edited or non-edited cell) to no longer be rendered after completing the edit */
			if (returnObject) {
				return val;
			}
			return (val || val === 0 || val === false) ? val.toString() : "&nbsp;";
		},
		_renderTemplatedCell: function (val, col) {
			var v, d = $.extend({}, val);
			/* M.H. 17 Jun 2013 Fix for bug #144484: When an unbound column has a template it crashes. */
			if (val === undefined || val === null || val[ col.key ] === undefined) {
				if (col.unbound === true) {
					v = "&nbsp;";
				} else {
					// M.H. 25 June 2015 Fix for bug 201616: Templated column "if" condition is not working for boolean columns
					v = this._renderCell(val, col, val, null, true);
				}
			} else {
				// M.H. 25 June 2015 Fix for bug 201616: Templated column "if" condition is not working for boolean columns
				v = this._renderCell(val[ col.key ], col, val, null, true);
			}
			d[ col.key ] = v;
			return this._tmplWrappers[ this.id() + "_" + col.key ]
				(d, this._jsrnd ? undefined : col.template);
		},
		_defaultTemplateFunc: function (d, tmpl) {
			return $.ig.tmpl(tmpl, d);
		},
		_setTemplateDefinition: function (jsrnd) {
			var i, key;
			for (i = 0; i < this.options.columns.length; i++) {
				if (this.options.columns[ i ].template && this.options.columns[ i ].template.length) {
					key = this.id() + "_" + this.options.columns[ i ].key;
					if (jsrnd) {
						// we'll add the template with  gridId.colKey
						$.templates(key, this.options.columns[ i ].template);
						this._jsrnd = true;
					} else {
						this._tmplWrappers[ key ] = this._defaultTemplateFunc;
					}
				}
			}
		},
		/* cross-browser calculation to check for the scrollbar width. we need
		this when creating the virtualization-enabled grid's layout */
		_scrollbarWidth: function () {
			// M.H. 1 Sep 2014 Fix for bug #177790: Column misalignment on touch devices when there is a igGrid.height set
			if (this._scrollbarWidthResolved === null ||
					this._scrollbarWidthResolved === undefined) {
				var $parent, $div = $("<div id=\"" + this.id() + "_tmp\"></div>")
					.css({ width: 50, height: 50, position: "absolute", top: -500, left: -500 })
					.prependTo("body")
					.append("<div></div>").find("div").css({ height: 100 }),
					w1, w2;
				/* L.A. 14 January 2013 - Fixing bug #130587. Horizontal scrollbar is
				missing when document mode is set to IE7 and a different browser mode is used. */
				if (document.documentMode === 7 || $.ig.util.isIE7) {
					w1 = $div.innerWidth();
					$div.parent().css("overflow-y", "scroll");
					w2 = $div.innerWidth();
					this._scrollbarWidthResolved = w1 - w2;
				} else {
					$parent = $div.parent();
					$parent.css({ overflow: "auto" });
					$div.css({ width: "100%" });
					/* M.H. 12 June 2014 Fix for bug #173336: Horizontal scrollbar does not appear
					when row virtualization is enabled when bootstrap(it is possible to happen such
					issue with other CSS framework) is applied then $parent is not with 50px width
					that's why I got the current width of the div */
					this._scrollbarWidthResolved = $parent.width() - $div.width();
				}
				$("#" + this.id() + "_tmp").remove();
				/* M.H. 2 Oct 2013 Fix for bug #153790: The grid is misaligned
				on touch devices when there is a vertical scrollbar */
				/*if (!this._scrollbarWidthResolved) {
				// fallback
					this._scrollbarWidthResolved = 17; // default
				} */
			}
			return this._scrollbarWidthResolved;
		},
		_fireInternalEvent: function (name, args) {
			var i, f, featureName, feature;
			for (i = 0; i < this.options.features.length; i++) {
				f = this.options.features[ i ];
				if (f !== undefined && f !== null && f.name !== undefined) {
					featureName = "igGrid" + f.name;
					feature = this.element.data(featureName);
					if (feature !== null && feature !== undefined &&
							feature[ name ]) {
						if (args) {
							feature[ name ](args);
						} else {
							feature[ name ]();
						}
					}
				}
			}
		},
		_initFeature: function (featureObject) {
			if (!featureObject) {
				return;
			}
			/* construct widget name */
			if (featureObject.name === undefined) {
				return;
			}
			var widget = "igGrid" + featureObject.name;
			/* validate widget */
			if ($.type(this.element[ widget ]) !== "function") {
				throw new Error($.ig.Grid.locale.noSuchWidget.replace("{featureName}", widget));
			}
			/* M.H. 3 July 2012 Fix for bug #115938 */
			if (this.element.data(widget)) {
				this.element[ widget ]("destroy");
			}
			/* instantiate widget */
			/* A.T. 4 Jan 2011 */
			this.element[ widget ](featureObject);
			this.element.data(widget)._injectGrid(this);
		},
		_initFeatureSettings: function (featureObject) {
			if (!featureObject) {
				return;
			}
			/* construct widget name */
			if (featureObject.name === undefined) {
				return;
			}
			var widget = "igGrid" + featureObject.name;
			/* validate widget */
			if ($.type(this.element[ widget ]) !== "function") {
				throw new Error($.ig.Grid.locale.noSuchWidget.replace("{featureName}", widget));
			}
			/* L.A. 31 July 2012 Fixing bug #113983 After a 'destroy' of a feature, 'dataBind' throws a js error */
			if (this.element.data(widget)) {
				this.element.data(widget)._injectGrid(this, true);
			}
		},
		_onFeaturesSoftDirty: function (e, args) {
			var i, feature;
			if (args.owner.options.type !== "remote") {
				return;
			}
			for (i = 0; i < this.options.features.length; i++) {
				feature = this.element.data("igGrid" + this.options.features[ i ].name);
				if (feature && feature !== args.owner && feature.options && feature.options.type === "local") {
					if (feature._onUIDirty && $.type(feature._onUIDirty) === "function") {
						feature._onUIDirty(e, args);
					}
				}
			}
		},
		/*jscs:disable*/
		_kval_from_key: function (key, data) {
			var k, k_val = "", i;
			if (key.indexOf(",") !== -1) {
				k = key.split(",");
				for (i = 0; i < k.length; i++) {
					k_val += data[ k[ i ] ];
					if (i < k.length - 1) {
						k_val += ",";
					}
				}
			} else {
				k_val = data[ key ];
			}
			return k_val;
		},
		/*jscs:enable*/
		/* returns key of row, or index of row, or last index of rows */
		_rowId: function (rec, index) {
			var key = this.options.primaryKey;
			/*jscs:disable*/
			key = key ? this._kval_from_key(key, rec) : null;
			/*jscs:enable*/
			if (!key) {
				key = index;
				if (key !== 0) {
					key = this.rows().length - 1;
				}
			}
			return key;
		},
		_inferOpType: function () {
			// infer the default feature operation type, if it is not explicitly specified by the developer
			// that will be done based on the data source type after it's analyzed
			if (this.options.dataSourceUrl || this.dataSource.type() === "remoteUrl") {
				return "remote";
			}
			return "local";
		},
		/* virtualization: branches the virtualization rendering according to the virtualizationMode */
		_renderVirtualRecords: function () {
			// trigger event so that features can clean up itself (end editing for example)
			var mode = this.options.virtualizationMode;
			this._trigger("virtualrendering");
			if (mode === undefined || mode === "") {
				mode = "continuous";
			}
			/* M.H. 10 April 2012 Fix for bug #107566 */
			if (mode === "fixed" || this.options.columnVirtualization === true) {
				if (this._persistVirtualScrollTop) {
					//M.K. Preserve scroll position after dataBind
					this._startRowIndex = Math.ceil(this._scrollContainer().scrollTop() /
						parseInt(this.options.avgRowHeight, 10));
					if (this._startRowIndex > this.dataSource.dataView().length - this._virtualRowCount) {
						//data source has changed to one with smaller size
						if (this.dataSource.dataView().length - this._virtualRowCount > 0) {
							this._startRowIndex = this.dataSource.dataView().length - this._virtualRowCount;
						} else {
							this._startRowIndex = 0;
						}
					}
				}
				this._renderVirtualRecordsFixed();
			} else if (mode === "continuous") {
				this._renderVirtualRecordsContinuous();
				/* trigger event so that features such as selection can apply the selection */
				/* M.H. 4 Dec 2013 Fix for bug #159020: Duplicated iggridvirtualrecordsrender event on changing pages */
				this._trigger("virtualrecordsrender", null, { owner: this, dom: this._virtualDom });
			}
		},
		_getHScrollContainerInner: function () {
			var ret = this._hscrollbarinner();
			if (ret.length === 0 && this.options.virtualizationMode === "continuous") {
				ret = this.container().find("#" + this.id() + "_horizontalScrollContainer div");
			}
			return ret;
		},
		_getScrollContainerHeight: function () {
			return this._scrollContainer().children(":first-child").height();
		},
		_getDisplayContainerHeight: function () {
			return this._vdisplaycontainer().height();
		},
		_getDisplayContainerWidth: function () {
			return this._vdisplaycontainer().width();
		},
		_setDisplayContainerWidth: function (width) {
			if (width === undefined) {
				return;
			}
			this._vdisplaycontainer().css("max-width", width);
			this._vdisplaycontainer().width(width);
		},
		_getVHeadersWidth: function () {
			return this.container().find("#" + this.id() + "_headers_v").width();
		},
		_setVHeadersWidth: function (width) {
			if (width === undefined) {
				return;
			}
			this.container().find("#" + this.id() + "_headers_v").width(width);
		},
		_setScrollContainerHeight: function (height) {
			var sc = this._scrollContainer();
			if (height === undefined) {
				return;
			}
			/*in IE there is limitation for max height that could be set for DIV container
			if it is set too big height - then div is not scrollable */
			if ($.ig.util.isIE && height > this.maxScrollContainerHeight) {
				this._setMaxHeightForScrollCntnr = true;
				height = this.maxScrollContainerHeight;
			}
			sc.children(":first-child").height(height);
			/* A.T. 15 Jul - need this hack for IE because of an IE bug
			that doesn't immediately update the scrollbar visually */
			/* M.K. Fix for bug 186556: In IE8 continuous virtualization
			will quickly change rows' height creating a flickering effect. */
			/*The previous hack for IE doesn't seem to be needed anymore.
			if ($.ig.util.isIE) {
				funcIEScroll = function () {
					sc.scrollTop(sc.scrollTop() + 2);
					sc.scrollTop(sc.scrollTop() - 2);
				};
				// M.H. 21 Mar 2014 Fix for bug #167904: Javascript runtime error on initial grid load with virtualization
				if ($.ig.util.browserVersion <= 8) {
					setTimeout(function () {
						funcIEScroll();
					}, 0);
				} else {
					funcIEScroll();
				}
			} */
		},
		_setScrollContainerScrollTop: function (top) {
			if (top === undefined) {
				return;
			}
			this._scrollContainer().scrollTop(top);
		},
		_getScrollContainerScrollTop: function () {
			return this._scrollContainer().scrollTop();
		},
		_setDisplayContainerScrollTop: function (top) {
			if (top === undefined) {
				return;
			}
			this._vdisplaycontainer().scrollTop(top);
		},
		_getDisplayContainerScrollTop: function () {
			return this._vdisplaycontainer().scrollTop();
		},
		_setDisplayContainerScrollLeft: function (left) {
			if (left === undefined) {
				return;
			}
			this._vdisplaycontainer().scrollLeft(left);
		},
		_getDisplayContainerScrollLeft: function () {
			return this._vdisplaycontainer().scrollLeft();
		},
		/* continuous virtualization: calculates average row height */
		_calculateAvgRowHeight: function () {
			var rowHeightSum = this.container().find("#" + this.id() + " > tbody").height();
			/* M.H. 25 Jul 2013 Fix for bug #146837: Vertical scroll
			bar does not display when control inside the Jquery UI tab */
			if (rowHeightSum === 0) {
				rowHeightSum = parseInt(this.options.height, 10);
			}
			/* M.H. 18 Jul 2013 Fix for bug #146756: On deleting a row,
			the rows shrink their height and occupy only 50% of the grid's
			height and Average row height doesnot get applied */
			if (this.options.virtualizationMode === "fixed") {
				if (this._fixedAvgRowHeight) {
					return this._fixedAvgRowHeight;
				}
				this._fixedAvgRowHeight = rowHeightSum / this._virtualRowCount;
			}
			return rowHeightSum / this._virtualRowCount;
		},
		/* continuous virtualization: gets the visible area of the displayed container */
		_getDisplayContainerVisibleArea: function () {
			var scrollTop = this._getDisplayContainerScrollTop(), height = this._getDisplayContainerHeight();
			return { top: scrollTop, bottom: (scrollTop + height) };
		},
		/* M.H. 19 Jun 2013 Fix for bug #142650: igGrid.virtualScrollTo API method is not working */
		virtualScrollTo: function (scrollerPosition) {
			/* Scroll to the specified row or specified position(in pixels)
				paramType="string|number" An identifier of the vertical scroll position. When it is string then it is interpreted as pixels otherwise it is the row number
			*/
			var avgRowHeight, $scrollContainer = this._scrollContainer(),
				pos = parseInt(scrollerPosition, 10);
			/* it is scrollerPosition is how much to be scrolled in pixels */
			if ($.type(scrollerPosition) !== "string") {
				/* M.H. 28 Feb 2014 Fix for bug #164139: IGGrid virtualScrollTo
				doesn't seem to be reliable for fixed and continuous virtualization */
				if (this.options.virtualizationMode === "fixed") {
					avgRowHeight = parseInt(this.options.avgRowHeight, 10);
				} else {
					if (this._avgRowHeight) {
						avgRowHeight = this._avgRowHeight;
					} else {
						avgRowHeight = this._calculateAvgRowHeight();
					}
				}
				scrollerPosition--;
				/* avgRowHeight =  Math.ceil(avgRowHeight); */
				pos = avgRowHeight * scrollerPosition;
				pos = Math.ceil(pos);
				/* M.H. 24 Feb 2014 Fix for bug #164139: IGGrid virtualScrollTo doesn't
				seem to be reliable for fixed and continuous virtualization */
				this._virtualScrollToIndex = scrollerPosition;
			}
			$scrollContainer.scrollTop(pos);
			/*if (!vfixed) {
				setTimeout(function () {
					if (avgRowHeight !== self._avgRowHeight) {
						//self._updateVirtualScrollContainer();
						//$scrollContainer.scrollTop(self._avgRowHeight * scrollerPosition);
					}
				}, 2);
			}*/
		},
		/* M.H. 19 Jun 2013 Fix for bug #142650: igGrid.virtualScrollTo API method is not working */
		/* continuous virtualization: tries to scroll, if not successful loads new portion of data */
		_virtualScrollToInternal: function (virtualScrollerY) {
			var tableId, firstRow, lastRow, scrollData, scrollResult;
			if (virtualScrollerY === this._oldScrollTop) {
				return;
			}
			tableId = "#" + this.id();
			/* M.H. 16 Oct 2014 Fix for bug #182885: When continuous virtualization
			is enabled and all levels are expanded teh scrollbar cannot reach the bottom */
			firstRow = this.container().find(tableId + " > tbody > tr:not([data-container]):visible:first");
			lastRow = this.container().find(tableId + " > tbody > tr:not([data-container]):visible:last");

			/* I.I. bug fix for 104538 */
			if (firstRow.length === 0 || lastRow.length === 0) {
				return;
			}
			scrollData = {
				virtualScrollerY: this._getScrollContainerScrollTop(),
				deltaScroll: virtualScrollerY - this._oldScrollTop,
				firstRow: firstRow,
				lastRow: lastRow,
				firstRowDataIndex: parseInt(firstRow.attr("data-row-idx"), 0),
				lastRowDataIndex: parseInt(lastRow.attr("data-row-idx"), 0),
				visibleArea: this._getDisplayContainerVisibleArea()
			};
			scrollResult = { action: "SA_INITIAL" };
			if (!this._tryScroll(scrollData, scrollResult)) {
				this._trigger("virtualrendering");
				this._rebuildVirtualRows(scrollData, scrollResult);
			}
			this._correctScrollPosition(this._getTotalRowsCount());
		},
		/* continuous virtualization: returns the length of the datasource; */
		/* this method is overriden by the groupby feature since groupby uses
		different datasource, hence the length is calculated differently */
		_getTotalRowsCount: function () {
			return this._getDataView().length;
		},
		/* continuous virtualization: tries to scroll to a given position and returns whether the scroll is successful. */
		/* If the scroll is not successful the method returns what action should be performed to display the required data */
		_tryScroll: function (scrollData, scrollResult) {
			var deltaScroll, firstRow, lastRow, displayContainerHeight, displayContainerScrollTop;
			if (scrollResult === undefined) {
				throw new Error("scrollResult parameter should be provided");
			}
			deltaScroll = scrollData.deltaScroll;
			firstRow = scrollData.firstRow;
			lastRow = scrollData.lastRow;
			displayContainerHeight = this._getDisplayContainerHeight();
			displayContainerScrollTop = this._getDisplayContainerScrollTop();
			/* In case of multi-row-layout in IE - we should make additional check for scrolled to bottom
			When scrolling slowly to bottom (and rebuildVirtualRecords is called few times with action SA_NEED_NEXT_PAGE)
			then it is possible to NOT scroll to last record */
			if (this._rlp &&
				this._isScrolledToBottomInVirtGrid() &&
				scrollData.lastRowDataIndex < this._totalRowCount) {
				scrollResult.action = "SA_NEED_LAST_PAGE";
				return false;
			}
			/* check if scroll is possible at all if this is a scroll down */
			/* check if the last virtual row would be within scrolling area if scrolled by deltaScroll */
			/* I.I. rework for bug 108439, 108641 and 109013 */
			if (deltaScroll > 0) {
				if (lastRow[ 0 ].offsetTop + lastRow.outerHeight() >=
					displayContainerScrollTop + deltaScroll + displayContainerHeight ||
					isNaN(scrollData.lastRowDataIndex) ||
					scrollData.lastRowDataIndex >= this._getLastVisibleDataRecordIndex()) {
					this._setDisplayContainerScrollTop(displayContainerScrollTop + deltaScroll);
					scrollResult.action = "SA_SUCCESSFUL";
					return true;
				}
				if (deltaScroll <= (this._avgRowHeight * this._virtualRowCount)) {
					/*  M.H. 24 Feb 2014 Fix for bug #164139: IGGrid virtualScrollTo doesn't
					seem to be reliable for fixed and continuous virtualization */
					if (deltaScroll + displayContainerHeight >
						(this._avgRowHeight * this._virtualRowCount)) {
						scrollResult.action = "SA_NEED_SOME_PAGE";
						return false;
					}
					scrollResult.action = "SA_NEED_NEXT_PAGE";
					return false;
				}
			}
			/* if this is a scroll up */
			/* check if the first virtual row would be within scrolling area if scrolled by deltaScroll */
			if (deltaScroll < 0) {
				if (firstRow[ 0 ].offsetTop <= displayContainerScrollTop + deltaScroll ||
						scrollData.firstRowDataIndex === 0) {
					this._setDisplayContainerScrollTop(displayContainerScrollTop + deltaScroll);
					scrollResult.action = "SA_SUCCESSFUL";
					return true;
				}
				if (Math.abs(deltaScroll) <= (this._avgRowHeight * this._virtualRowCount)) {
					scrollResult.action = "SA_NEED_PREV_PAGE";
					return false;
				}
			}
			scrollResult.action = "SA_NEED_SOME_PAGE";
			return false;
		},
		_getDataView: function () {
			return this.dataSource.dataView();
		},
		_isScrolledToBottomInVirtGrid: function () {
			/* Detects whether grid is scrolled to bottom. This function is used only when rowVirtualization is enabled and virtualizationMode is continuous */
			var scrlCntnr = this._scrollContainer();
			return this._getScrollContainerScrollTop() + 5 >=
				scrlCntnr[ 0 ].scrollHeight - scrlCntnr.innerHeight();
		},
		/* continuous virtualization: gets called each time when after
		scrolling current TRs are not sufficient to display the required data */
		_rebuildVirtualRows: function (scrollData, scrollResult) {
			var noCancel, virtualScrollerY, deltaScroll, tableId, visibleArea, tbody,
				scrlCntnr, oAvgRowHeight, expandedRowsHeight, trs, i, scrollerHeight,
				firstVisibleTR, firstVisibleTRRelativeOffset, startIndexToBeLoaded,
				newFirstVisibleRow, displayContainerScrollTop, maxScrollPos, avgRowHeight,
				tr, lastVisibleTR, lastVisibleTRIndex, firstVisibleTRIndex, endIndexToBeLoaded,
				dataRowIndexToBeFirst, hasOverflow;
			noCancel = this._trigger("rebuildingvirtualrows", null, {
				owner: this,
				scrollData: scrollData,
				scrollResult: scrollResult
			});
			if (noCancel === false) {
				return;
			}
			virtualScrollerY = scrollData.virtualScrollerY;
			deltaScroll = scrollData.deltaScroll;
			tableId = "#" + this.id();
			visibleArea = scrollData.visibleArea;
			tbody = this.container().find(tableId + " > tbody");
			expandedRowsHeight = 0;
			trs = this.container().find(tableId + " > tbody > tr[data-container=\"true\"]");
			for (i = 0; i < trs.length; i++) {
				expandedRowsHeight += $(trs[ i ]).outerHeight();
			}
			if (expandedRowsHeight > 0) {
				scrollerHeight = this._getScrollContainerHeight();
				this._setScrollContainerHeight(scrollerHeight - expandedRowsHeight);
			}
			/* Fix issue with multi-row-layout in IE when scrolled to bottom because when
			scrolling slowly(and a few times rebuildVirtualRecords is called with action 'SA_NEED_NEXT_PAGE')
			then it is possible to NOT scroll to last record */
			if (deltaScroll > 0 && scrollResult.action === "SA_NEED_LAST_PAGE") {
				startIndexToBeLoaded = this._getDataView().length - this._virtualRowCount;
				endIndexToBeLoaded = startIndexToBeLoaded + this._virtualRowCount - 1;
				tbody.empty();
				this._startRowIndex = startIndexToBeLoaded;
				this._startColIndex = 0;
				this._renderRecords(startIndexToBeLoaded, endIndexToBeLoaded);
				if (!this._persistVirtualScrollTop) {
					this._setDisplayContainerScrollTop(10000);
				}
			}
			/* next virtual page needed */
			if (deltaScroll > 0 && scrollResult.action === "SA_NEED_NEXT_PAGE") {
				firstVisibleTR = this._getFirstVisibleTR(visibleArea);
				/* get first visible TR offset relative to visible area */
				firstVisibleTRRelativeOffset = Math.abs(firstVisibleTR[ 0 ].offsetTop - visibleArea.top);
				/* first loaded data row becomes the row which was first visble before */
				startIndexToBeLoaded = parseInt(firstVisibleTR.attr("data-row-idx"), 0);
				dataRowIndexToBeFirst = startIndexToBeLoaded;
				endIndexToBeLoaded = parseInt(this._virtualRowCount, 0) + startIndexToBeLoaded - 1;
				hasOverflow = false;
				if (endIndexToBeLoaded > this._getDataView().length - 1) {
					endIndexToBeLoaded = this._getDataView().length - 1;
					startIndexToBeLoaded = endIndexToBeLoaded - this._virtualRowCount + 1;
					hasOverflow = true;
				}
				tbody.empty();
				this._startRowIndex = startIndexToBeLoaded;
				this._startColIndex = 0;
				this._renderRecords(startIndexToBeLoaded, endIndexToBeLoaded);
				if (hasOverflow) {
					tr = this.container()
						.find(tableId + " > tbody > tr[data-row-idx='" + dataRowIndexToBeFirst + "']");
					firstVisibleTRRelativeOffset = tr[ 0 ].offsetTop + firstVisibleTRRelativeOffset;
				}
				/* scroll to where last visible row was and add the new scrolling */
				this._setDisplayContainerScrollTop(firstVisibleTRRelativeOffset + deltaScroll);
			}
			/* prev virtual page needed */
			if (deltaScroll < 0 && scrollResult.action === "SA_NEED_PREV_PAGE") {
				//get last visible tr -> lastTR
				lastVisibleTR = this._getLastVisibleTR(visibleArea);
				lastVisibleTRIndex = parseInt(lastVisibleTR.attr("data-row-idx"), 0);
				firstVisibleTR = this._getFirstVisibleTR(visibleArea);
				firstVisibleTRIndex = parseInt(firstVisibleTR.attr("data-row-idx"), 0);
				/* get its data index */
				endIndexToBeLoaded = lastVisibleTRIndex;
				startIndexToBeLoaded = endIndexToBeLoaded - parseInt(this._virtualRowCount, 0) + 1;
				/* TODO: if start is < 0, recalc */
				if (startIndexToBeLoaded < 0) {
					startIndexToBeLoaded = 0;
					endIndexToBeLoaded = startIndexToBeLoaded + this._virtualRowCount - 1;
				}
				/* rebind from dataIndex - this._virtualRowsCount to dataIndex */
				tbody.empty();
				/* M.H. 27 Feb 2015 Fix for bug #189724: When deleting rows with
				continuous virtualization the rendered rows are incorrect */
				this._startRowIndex = startIndexToBeLoaded;
				this._renderRecords(startIndexToBeLoaded, endIndexToBeLoaded);
				tableId = "#" + this.id();
				newFirstVisibleRow = this.container()
					.find(tableId + " > tbody > tr[data-row-idx=\"" + firstVisibleTRIndex + "\"]");
				this._setDisplayContainerScrollTop(newFirstVisibleRow[ 0 ].offsetTop + deltaScroll);
			}
			if (scrollResult.action === "SA_NEED_SOME_PAGE") {
				startIndexToBeLoaded = Math.floor(virtualScrollerY / this._avgRowHeight);
				scrollerHeight = this._getScrollContainerHeight();
				/* in IE max scroll top and max height of DIV is about 1.5 MLN */
				/* M.H. 9 Dec 2015 Fix for bug 210968: In IE, last rows are not displayed
				in igGrid when rowVirtualization is true and virtualizationMode is continuous. */
				if (($.ig.util.isIE || this._setMaxHeightForScrollCntnr || this._rlp) && // in IE there is limitation for max height that could be set for DIV container
					this._totalRowCount * this._avgRowHeight >= scrollerHeight + 2) {
						/* when scrolled to the bottom(ensure that it will be shown last records) */
					if (this._isScrolledToBottomInVirtGrid()) {
							startIndexToBeLoaded = this._getDataView().length - this._virtualRowCount;
						} else {
							startIndexToBeLoaded = Math.ceil((virtualScrollerY / scrollerHeight) *
								this._totalRowCount);
						}
					}
				endIndexToBeLoaded = startIndexToBeLoaded + this._virtualRowCount - 1;
				if (startIndexToBeLoaded < 0) {
					startIndexToBeLoaded = 0;
					endIndexToBeLoaded = startIndexToBeLoaded + this._virtualRowCount - 1;
				}
				if (endIndexToBeLoaded >= this._getDataView().length - 1) {
					endIndexToBeLoaded = this._getDataView().length - 1;
					startIndexToBeLoaded = endIndexToBeLoaded - this._virtualRowCount + 1;
				}
				tbody.empty();
				/* M.H. 27 Feb 2015 Fix for bug #189724: When deleting rows with
				continuous virtualization the rendered rows are incorrect */
				this._startRowIndex = startIndexToBeLoaded;
				this._renderRecords(startIndexToBeLoaded, endIndexToBeLoaded);
				displayContainerScrollTop = 0;
				maxScrollPos = scrollerHeight - this._getDisplayContainerHeight();
				if (maxScrollPos - 3 <= virtualScrollerY && virtualScrollerY <= maxScrollPos + 3) {
					displayContainerScrollTop = 10000;
				}
				if (!this._persistVirtualScrollTop) {
					this._setDisplayContainerScrollTop(displayContainerScrollTop);
				}
			}
			/* if (expandedRowsHeight > 0) {
				this._setScrollContainerHeight (this._getScrollContainerHeight() - expandedRowsHeight);
			} */
			avgRowHeight = this._calculateAvgRowHeight();
			if (avgRowHeight > this._avgRowHeight) {
				oAvgRowHeight = this._avgRowHeight;
				this._avgRowHeight = avgRowHeight;
				this._trigger("avgRowHeightChanged", null, {
					owner: this,
					oAvgRowHeight: oAvgRowHeight,
					avgRowHeight: this._avgRowHeight
				});
			}
			this._trigger("virtualrecordsrender", null, {
				owner: this,
				tbody: tbody,
				dom: this._virtualDom
			});
		},
		/* continuous virtualization: corrects the virtual scroller position if needed. */
		/* this is necessary because virtualization uses approximation to
		scroll to a certain row, since the rows height is not constant */
		_correctScrollPosition: function (totalRowCount) {
			var scrollTop, maxScrollPos, tableId, firstRow,
				firstRowDataIndex, lastRow, lastRowDataIndex,
				visibleArea, tbody, rowsLeft,
				displayContainerScrollTop, targetRow, delta;
			scrollTop = this._getScrollContainerScrollTop();
			maxScrollPos = this._getScrollContainerHeight() - this._getDisplayContainerHeight();
			tableId = "#" + this.id();
			firstRow = this.container().find(tableId + " > tbody > tr:first");
			firstRowDataIndex = parseInt(firstRow.attr("data-row-idx"), 0);
			lastRow = this.container().find(tableId + " > tbody > tr:last");
			lastRowDataIndex = parseInt(lastRow.attr("data-row-idx"), 0);
			visibleArea = this._getDisplayContainerVisibleArea();
			tbody = this.container().find(tableId + " > tbody");
			if (scrollTop === 0) {
				if (visibleArea.top > 0) {
					this._scrollTo(visibleArea.top, true);
				} else if (firstRowDataIndex > 0) {
					this._scrollTo(firstRowDataIndex * this._avgRowHeight, true);
				}
			} else if (scrollTop >= maxScrollPos - 3 && scrollTop <= maxScrollPos + 3) {
				if (visibleArea.bottom < tbody.height() && lastRowDataIndex === totalRowCount - 1) {
					this._scrollTo(this._getScrollContainerScrollTop() -
					(tbody.height() - visibleArea.bottom), true);
				} else if (lastRowDataIndex < totalRowCount - 1) {
					rowsLeft = totalRowCount - lastRowDataIndex;
					/* this._scrollTo(this._getScrollContainerScrollTop() - (rowsLeft * this._avgRowHeight), true); */
					this._setScrollContainerHeight(this._getScrollContainerHeight() +
						(rowsLeft * this._avgRowHeight));
				}
			}
			displayContainerScrollTop = this._getDisplayContainerScrollTop();
			if (displayContainerScrollTop === 0 &&
				firstRowDataIndex === 0 &&
				this._getScrollContainerScrollTop() !== 0) {
				if (!this._persistVirtualScrollTop) {
					this._setScrollContainerHeight(this._totalRowCount * this._avgRowHeight);
					this._scrollTo(this._getScrollContainerScrollTop(), false);
				} else {
					this._scrollTo(0, true);
				}

			}
			if (displayContainerScrollTop === (tbody.height() - this._getDisplayContainerHeight()) &&
				lastRowDataIndex === totalRowCount - 1) {
				this._scrollTo(this._getScrollContainerHeight(), true);
			}
			/* M.H. 24 Feb 2014 Fix for bug #164139: IGGrid virtualScrollTo
			doesn't seem to be reliable for fixed and continuous virtualization */
			if (this._virtualScrollToIndex !== undefined && this._virtualScrollToIndex !== null) {
				//this._scrollTo(scrollTop - firstRow.scrollTop(), true);
				targetRow = this.container().find(tableId + " > tbody > tr[data-row-idx=" +
					this._virtualScrollToIndex + "]");
				delta = 0;
				if (targetRow.length === 1 && firstRowDataIndex !== this._virtualScrollToIndex) {
					delta = targetRow.offset().top - firstRow.offset().top;
				}
				this._virtualScrollToIndex = null;
				if (delta > 0) {
					this._vdisplaycontainer().scrollTop(delta);
				}
			}
		},
		/* continuous virtualization: used when correcting the virtual scroll position */
		_scrollTo: function (scrollTop, suppressScrollEvent) {
			if (suppressScrollEvent) {
				this._suppressScroll = true;
			}
			this._setScrollContainerScrollTop(scrollTop);
		},
		/* continuous virtualization: returns the first TR in the grid which is partly or fully visible */
		_getFirstVisibleTR: function (visibleArea) {
			var tableId = "#" + this.element[ 0 ].id, firstVisibleTR, visibleAreaTop = visibleArea.top;

			$(tableId + " > tbody > tr:visible").each(function () {
				firstVisibleTR = $(this);
				if (this.offsetTop + firstVisibleTR.height() > visibleAreaTop) {
					return false;
				}
			});
			/*firstVisibleTR = $(tableId + ' > tbody > tr:visible').filter(function (index) {
				return this.offsetTop + $(this).height() > visibleArea.top;
			}).first(); */
			return firstVisibleTR;
		},
		/* continuous virtualization: returns the last TR in the grid which is partly or fully visible */
		_getLastVisibleTR: function (visibleArea) {
			var tableId = "#" + this.id(), lastVisibleTR;
			lastVisibleTR = this.container().find(tableId + " > tbody > tr:visible")
				.filter(function () {
					return this.offsetTop < visibleArea.bottom;
				}).last();
			return lastVisibleTR;
		},
		/* continuous virtualization: determines total number
		of rows which are used by the virtualization */
		_determineVirtualRowCount: function () {
			var div, rowNumber = 10, ds, rows = "", html, i, height, rrFunc,
				avgRowHeight, displayContainerHeight, rowsPerPage, result, $colgroup;
			ds = this._getDataView();
			if (rowNumber > ds.length) {
				rowNumber = ds.length;
			}
			div = $("<div class=\"" + this.css.gridClasses + " " + this.css.baseClass + "\"></div>)")
				.appendTo("body")
				.css({
					position: "absolute",
					top: -1800,
					left: -1800,
					visibility: "hidden"
				});
			/* average row height (in case of multi-row-layout) should be calculated for DATA record(not of single DOM row) */
			if (this._rlp) {
				rrFunc = this._renderRecordFromLayout;
			} else {
				rrFunc = this._renderRecord;
			}
			for (i = 0; i < rowNumber; i++) {
				rows += rrFunc.apply(this, [ ds[ i ], i ]);
			}
			/* M.H. 28 Nov 2014 Fix for bug #185811: User can't scroll down with
			mouse wheel if continuous virtualization is enabled and binding specific data. */
			html = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"" +
				(this._isMultiRowGrid() ? this.css.mrlGridTableClass : this.css.gridTableClass) + "\">";
			/* M.H. 25 Mar 2015 Fix for bug 190908: User can't scroll down with
			mouse wheel if continuous virtualization is enabled and binding specific data. */
			/* M.H. 24 July 2015 Fix for bug 203369: Grid layout breaks
			when grouping by some column and virtualization is enabled */
			$colgroup = this.element.children("colgroup");
			if ($colgroup.length) {
				html += $colgroup[ 0 ].outerHTML;
			}
			html += "<tbody role=\"rowgroup\">" + rows + "</tbody></table>";
			div.html(html);
			height = div.height();
			avgRowHeight = height / rowNumber;
			displayContainerHeight = this._getDisplayContainerHeight();
			rowsPerPage = displayContainerHeight / avgRowHeight;
			result = Math.ceil(rowsPerPage * 3);
			div.remove();
			this._fireInternalEvent("_virtRowCountDetermined", result);
			return result;
		},
		_getTotalRowCount: function () {
			return this.dataSource.dataView().length;
		},
		/* continuous virtualization: called when grid tries to render its rows for first time */
		_renderVirtualRecordsContinuous: function () {
			var dvLen, firstVisibleTRIndex, oAvgRowHeight,
				endIndexToBeLoaded, lastVisibleRow, isScrolledToBottom;
			this._totalRowCount = this._getTotalRowCount();
			/* M.H. 9 Feb 2016 Fix for bug 213850: In case of rowVirtualization is enabled
			and scrolled to bottom when adding new row then grid is scrolled at incorrect position */
			this._oldScrollTop = 0;
			if (this._totalRowCount === 0) {
				this._setScrollContainerHeight(0);
				this._virtualDom = [];
				/* return; */
			}
			this._virtualRowCount = this._determineVirtualRowCount();
			if (this._virtualRowCount > this._totalRowCount) {
				this._virtualRowCount = this._totalRowCount;
			}
			/* M.K. Preserve scroll position after dataBind if persistVirtualScrollTop option is tr */
			if (!this._persistVirtualScrollTop) {
				this._scrollTo(0, true);
				this._renderRecords(this._virtualRowCount - 1);
				oAvgRowHeight = this._avgRowHeight;
				this._avgRowHeight = this._calculateAvgRowHeight();
				if (this._avgRowHeight !== oAvgRowHeight) {
					this._trigger("avgRowHeightChanged", null, {
						owner: this,
						oAvgRowHeight: oAvgRowHeight,
						avgRowHeight: this._avgRowHeight
					});
				}
				this._setScrollContainerHeight(this._totalRowCount * this._avgRowHeight);
			} else {
				dvLen = this._getDataView().length;
				if (this._prevFirstVisibleTRIndex || this._prevFirstVisibleTRIndex === 0) {
					firstVisibleTRIndex = this._prevFirstVisibleTRIndex;
				} else {
					firstVisibleTRIndex = 0;
				}
				if (firstVisibleTRIndex >= dvLen - 1) {
					endIndexToBeLoaded = dvLen - 1;
					firstVisibleTRIndex = dvLen - this._virtualRowCount - 1;
				} else {
					endIndexToBeLoaded = parseInt(this._virtualRowCount, 0) + firstVisibleTRIndex;
				}
				if (endIndexToBeLoaded > dvLen - 1) {
					endIndexToBeLoaded = dvLen - 1;
					firstVisibleTRIndex = dvLen - this._virtualRowCount - 1;
				}
				if (firstVisibleTRIndex < 0) {
					firstVisibleTRIndex = 0;
				}
				/* this._avgRowHeight = this._calculateAvgRowHeight(); */
				/* this._setScrollContainerHeight(this._totalRowCount * this._avgRowHeight); */
				this._renderRecords(firstVisibleTRIndex, endIndexToBeLoaded);
				lastVisibleRow = this.container().find("#" + this.id() + " > tbody > tr:visible:last");
				isScrolledToBottom = this._getScrollContainerScrollTop() + 5 >
					this._scrollContainer()[ 0 ].scrollHeight - this._scrollContainer().innerHeight();
				if (endIndexToBeLoaded === dvLen - 1 && isScrolledToBottom) {
					//scrollbar is at the bottom, last row should be visible.
					this._setDisplayContainerScrollTop(lastVisibleRow.length ?
						lastVisibleRow[ 0 ].offsetTop : 0);
				} else {
					this._setDisplayContainerScrollTop(this._prevFirstVisibleTROffset);
				}
			}
		},
		/* just rerenders currently rendered virtual rows; usefull when delete a row from updating feature */
		_rerenderVirtualRecordsContinuous: function () {
			var tableId = "#" + this.id(), lastRow, lastRowDataIndex;
			/* firstRow = $(tableId + ' > tbody > tr:first'); */
			lastRow = this.container().find(tableId + " > tbody > tr:last");
			/* S.S. April 24, 2013 Bug #140677. We'll get the first index
			by the internal this._startRowIndex var so that when the tr-s are
			changed (such as when some are deleted) the proper amount of rows is rendered */
			/* firstRowDataIndex = parseInt(firstRow.attr('data-row-idx'), 0); */
			lastRowDataIndex = parseInt(lastRow.attr("data-row-idx"), 0);
			this._startRowIndex = this._startRowIndex || 0;
			this._renderRecords(this._startRowIndex, lastRowDataIndex);
			this._trigger("virtualrecordsrender", null, {
				owner: this,
				tbody: this.element.children("tbody"),
				dom: this._virtualDom
			});
		},
		/*_virtualDom should be built because features like selection expects it */
		_buildVirtualDomForContinuousVirtualization: function () {
			var rows, cells, i, j;
			if (this.options.virtualization === true &&
				this.options.virtualizationMode === "continuous") {
				this._virtualDom = [];
				rows = this.container().find("#" + this.id() + " > tbody > tr");
				for (i = 0; i < rows.length; i++) {
					this._virtualDom[ i ] = [];
					cells = rows[ i ].children;
					for (j = 0; j < cells.length; j++) {
						this._virtualDom[ i ][ j ] = cells[ j ];
					}
				}
				/* A.T. 12 July Bug #146756 */
				this._updateVirtualScrollContainer();
			}
		},
		/* returns true if TD(DOM element passed as an argument) is in fixed container */
		_isFixedElement: function ($td) {
			if (this.hasFixedColumns() && $td.closest("div").attr("data-fixed-container") !== undefined) {
				return true;
			}
			return false;
		},
		getColumnByTD: function ($td) {
			/* Returns column object and visible index for the table cell(TD) which is passed as argument
				paramType="object" cell(TD) - either DOM TD element or jQuery object
				returnType="object" object that contains the column object and the visible index to which the cell belongs to
			*/
			/* returns column object and visible index for the table cell(TD) which is passed as argument
			table cell should be TD(jQuery object). If it has attributes data-skip or data-parent returns null */
			if (!($td instanceof jQuery)) {
				$td = $($td);
			}
			var $tr = $td.closest("tr"), describedBy = $td.attr("aria-describedBy"),
				column, res = {}, visibleInd, i;
			if ($tr.length === 0) {
				return null;
			}
			if ($td.attr("data-parent") || $td.attr("data-skip")) {
				return null;
			}
			if (!describedBy) {
				return { column: this._visibleColumns()[ 0 ], index: 0 };
			}
			describedBy = describedBy.split(" ");
			for (i = 0; i < describedBy.length; i++) {
				column = describedBy[ i ].trim();
				if (column.startsWith(this.id() + "_")) {
					column = this.columnByKey(column.slice(column.indexOf(this.id() + "_") +
						this.id().length + 1));
					break;
				}
			}
			visibleInd = this.getVisibleIndexByKey(column.key, true);
			res = { column: column, index: visibleInd };
			return res;
		},
		_clearPersistenceData: function () {
			var topmostGrid = this.element.closest(".ui-iggrid-root").data("igGrid") || this;
			delete topmostGrid.persistenceData;
		},
		_savePersistenceData: function (data, feature, layout) {
			// layout is ID(taken from the ID of the DOM element) of the specific child layout
			var topmostGrid = this, featureId = feature;
			if ($.type(layout) === "string" && layout.length > 0) {
				topmostGrid = this.element.closest(".ui-iggrid-root").data("igGrid") || this;
				if (layout !== topmostGrid.element[ 0 ].id) {
					featureId = feature + "_" + layout;
				}
			}
			/* init persistenceData for the specified feature(if not) */
			topmostGrid.persistenceData = topmostGrid.persistenceData || {};
			topmostGrid.persistenceData[ featureId ] = data;
		},
		_getPersistenceData: function (feature, layout) {
			var topmostGrid = this, featureId = feature;
			if ($.type(layout) === "string" && layout.length > 0) {
				topmostGrid = this.element.closest(".ui-iggrid-root").data("igGrid") || this;
				if (layout !== topmostGrid.element[ 0 ].id) {
					featureId = feature + "_" + layout;
				}
			}
			topmostGrid.persistenceData = topmostGrid.persistenceData || {};
			return topmostGrid.persistenceData[ featureId ];
		},
		_saveFirstVisibleTRIndex: function () {
			var fvtr = this._getFirstVisibleTR(this._getDisplayContainerVisibleArea());
			if (fvtr && fvtr.length) {
				this._prevFirstVisibleTRIndex = parseInt(fvtr.attr("data-row-idx"), 10);
				this._prevFirstVisibleTROffset = this._getDisplayContainerScrollTop() - fvtr[ 0 ].offsetTop;
			}
		},
		_getLastVisibleDataRecordIndex: function () {
			//M.K. This method return the index of the last record that is expected to be visible.
			// This method is overriden in groupby
			return this._getDataView().length - 1;
		},
		_revertToInitialState: function () {
			// restore initial state of the DOM element on which the grid is instantiated - like attributes and (if table) colgroup, tfoot, thead
			var i, a, attr;
			/* M.H. 8 Jan 2014 Fix for bug #159857: Calling igGrid.destroy
			doesn't remove the COLGROUP, TFOOT and THEAD tags as well as
			some attributes on the placeholder */
			if (this.element.is("table") && this._initialChildren) {
				this._initialChildren.appendTo(this.element);
			}
			attr = this.element[ 0 ].attributes;
			a = [];
			for (i = 0; i < attr.length; i++) {
				if (attr[ i ].name !== "id") {
					a.push(attr[ i ].name);
				}
			}
			for (i = 0; i < a.length; i++) {
				this.element.removeAttr(a[ i ]);
			}
			for (i = 0; i < this._initialAttributes.length; i++) {
				if (this._initialAttributes[ i ].name !== "id") {
					this.element.attr(this._initialAttributes[ i ].name, this._initialAttributes[ i ].value);
				}
			}
		},
		_removeDetachedDOM: function (container) {
			if (!container) {
				return;
			}
			/* we need to remove DOM element from detachedHeaders/detachedFooters */
			var colKey, arr, i;
			for (colKey in container) {
				if (container.hasOwnProperty(colKey)) {
					arr = container[ colKey ];
					for (i = 0; i < arr.length; i++) {
						if (arr[ i ] && arr[ i ].length) {
							arr[ i ].remove();
						}
					}
				}
			}
		},
		_detachEvents: function () {
			var container;
			if (this._cellClickHandler) {
				this.element.unbind({
					"click": this._cellClickHandler
				});
				this._cellClickHandler = null;
			}
			/* M.H. 3 Mar 2015 Fix for bug #188215: Memory leak occurs in Sorting feature */
			if (this._mouseClickEventHandlers) {
				this.element.unbind(this._mouseClickEventHandlers);
				delete this._mouseClickEventHandlers;
			}
			if (this._uiSoftDirtyHandler) {
				this.element.unbind("iggriduisoftdirty", this._uiSoftDirtyHandler);
				this._uiSoftDirtyHandler = null;
			}
			if (this._hovEvts) {
				this.element.unbind(this._hovEvts);
				this._hovEvts = null;
			}
			if (this._documentEvents) {
				$(document).unbind(this._documentEvents);
				this._documentEvents = null;
			}
			container = this.scrollContainer();
			if (!container.length) {
				container = this._vdisplaycontainer();
			}
			/* M.H. 11 June 2015 Fix for bug 201082: When row virtualization is
			enabled and cell is focused pressing TAB key to move the focus the
			cells and headers get misaligned. */
			/* unbind data container */
			container.unbind(".syncScrollContainers");
			this.headersTable().parent().unbind(".syncScrollContainers");
			container = this.footersTable().closest("div");
			container.unbind(".syncScrollContainers");
			this.element.unbind(".setFocusElement");
		},
		_destroyFeatures: function () {
			var i, features = this.options.features, e = this.element;
			if (this._internalFeatures && this._internalFeatures.length) {
				features = features.concat(this._internalFeatures);
			}
			/* A.T. 1 Sept. 2011 - Fix for bug #85486 */
			for (i = 0; i < features.length; i++) {
				/* L.A. 20 February 2013 - jQuery 1.9 compatibility
				Don"t call destroy on non existing widget (or already destroyed one) */
				if (e.data("igGrid" + features[ i ].name)) {
					e[ "igGrid" + features[ i ].name ]("destroy");
				}
			}
		},
		destroy: function (notToCallDestroy) {
			/* destroy is part of the jQuery UI widget API and does the following:
				1. Remove custom CSS classes that were added.
				2. Unwrap any wrapping elements such as scrolling divs and other containers.
				3. Unbind all events that were bound.
			*/
			/* we need to make sure we check if the element has siblings, if it doesn't we append the actual this.element to its parent
			this is necessary to return everything exactly to its previous state in the DOM tree
			we first want to destroy all features */
			var prev = this.container().prev(), prepend = false;
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			if (this._scrollHeader && this._hscrollbarcontent().length > 0) {
				this._hscrollbarcontent().unbind("scroll", $.proxy(this._scrollHeader, this));
			}
			/* M.K unbind scroll from scrollcontainer */
			if (this.scrollContainer()) {
				this.scrollContainer().unbind("scroll");
				this.scrollContainer().unbind("wheel");
			}

			/* M.K. 5/7/2015 Fix for bug 192694: Destroying igGrid while it requests the remote data will result in JavaScript error */
			if (this.dataSource._ajaxRequest && this.dataSource._ajaxRequest.readyState !== 4) {
				this.dataSource._ajaxRequest.abort();
			}
			this._headerInitCallbacks = [];
			this._footerInitCallbacks = [];
			this.tmpDataSource = null;
			if (this._resId) {
				clearInterval(this._resId);
			}
			this._destroyFeatures();
			if (this._detachedContainersInitialized) {
				this._removeDetachedDOM(this._detachedHeaderCells);
				delete this._detachedHeaderCells;
				this._removeDetachedDOM(this._detachedFooterCells);
				delete this._detachedFooterCells;
				delete this._detachedContainersInitialized;
			}
			this._detachEvents();
			/* if grid is instantiated on DIV element - then this.element is <table> with id <gridId>_table */
			if (this._isWrapped && this.container().parent().data("igGrid")) {
				this.element = this.container().parent();
				this.element.empty();
				if (notToCallDestroy !== true) {
					$.Widget.prototype.destroy.call(this);
				}
				this._revertToInitialState();
				this.element.trigger(this.events.destroyed, { owner: this });
				return this;
			}
			/* if instantiated on table */
			if (prev.length === 0) {
				prev = this.container().parent();
				prepend = true;
			}
			this.element.empty();
			/* this.element is cleared so clone is not so heavy */
			if (prepend) {
				prev.prepend(this.element);
			} else {
				//prev.append(this.element);
				// L.A. 10 May 2012 Bug #110557
				this.element.insertAfter(prev);
			}
			if (notToCallDestroy !== true) {
				$.Widget.prototype.destroy.call(this);
			}
			this.container().remove();
			/* M.H. 15 June 2015 Fix for bug 201113: When the grid has defined height,
			calling renderMultiColumnHeader API removes the root level header and grid
			container styles when renderMultiColumnHeader is called this._container should
			be set to NULL otherwise if it is used this.container() - it is taken
			elements from the cashed element */
			this._container = null;
			this._revertToInitialState();
			/* it should not be triggered event destroyed when renderMultiColumnHeader is called */
			if (notToCallDestroy === true) {
				return this;
			}
			/* M.H. 29 Oct 2012 Fix for bug #120642 */
			this.element.trigger(this.events.destroyed, { owner: this });
			return this;
		}
	});
	$.extend($.ui.igGrid, { version: "16.1.20161.2145" });
}(jQuery));
/*jshint +W106 */



