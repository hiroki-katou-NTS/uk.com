/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmf001.f.viewmodel {
  
  /** 受入ドメインID */
  type DomainId = number;

  /** 受入項目No */
  type ItemNo = number;

  /** 受入元 */
  const SourceType = {
    Csv: 0,
    Fixed: 1,
  };
  type SourceType = typeof SourceType[keyof typeof SourceType];

  /** CSV項目No */
  type CsvColumnNo = number;

	@bean()
	class ViewModel extends ko.ViewModel {

    /** 受入設定コード */
    settingCode: string;

    /** 画面起動時に初期選択するドメイン */
    defaultDomainId: DomainId;

    /** すべてのドメイン（ここから選択可能）*/
    allDomains: KnockoutObservableArray<DomainIdEnum> = ko.observableArray(__viewContext.enums.ImportingDomainId);
    selectedIdInAllDomains: KnockoutObservable<DomainId> = ko.observable();

    /** 受入の対象に設定されたドメイン一覧 */
    targetDomains: KnockoutComputed<ImportableDomain[]>;

    /** 選択中の受入対象ドメインID（GridListはvalueをstringに変換してしまうのでstring） */
    selectedIdInTargetDomains: KnockoutObservable<string> = ko.observable();

    /** 受入対象ドメインを選択しているか */
    isSelectedTargetDomain: KnockoutComputed<boolean>;

    /** 選択中の受入対象ドメイン */
    selectedTargetDomain: KnockoutComputed<ImportableDomain>;

    /** サンプルCSVデータ */
    sampleCsvItems: KnockoutObservableArray<SampleCsvItem> = ko.observableArray();

    /** 受入対象レイアウトのリスト */
    layouts: KnockoutObservableArray<Layout> = ko.observableArray([]);

    /** 選択中の受入レイアウト */
    selectedLayout: KnockoutComputed<Layout>;

    /** 受入可能項目のキャッシュ */
    importableItems: { [key: DomainId]: dto.ImportableItemDto[] } = {};

    
    constructor() {
      super();
        
			var params = __viewContext.transferred.get();

			this.settingCode = params.settingCode;
      this.defaultDomainId = params.domainId;

      this.targetDomains = ko.computed(() => {
        let domains = this.layouts().map(l => new ImportableDomain(l.domainId));
        domains.sort((a, b) => a.domainId - b.domainId);
        return domains;
      });

      this.isSelectedTargetDomain = ko.computed(() => {
        let id = this.selectedIdInTargetDomains();
        return id !== undefined && id !== "" && id !== "undefined";
      })

      this.selectedTargetDomain = ko.computed(() => {
        let id = Number(this.selectedIdInTargetDomains());
        return this.targetDomains().filter(d => d.domainId === id)[0];
      });

      this.selectedLayout = ko.computed(() => {
        if (!this.isSelectedTargetDomain()) return undefined;
        let id = Number(this.selectedIdInTargetDomains());
        return this.layouts().filter(l => l.domainId === id)[0];
      });
    }

    mounted(): void {

      this.loadSetting().done(() => {
        this.selectedIdInTargetDomains("" + this.defaultDomainId);
      });
    }

    loadSetting(): JQueryPromise<any> {

      let dfd = $.Deferred();

      request.ajax("screen/com/cmf/cmf001/f/setting/" + this.settingCode).done(res => {

        Object.keys(res.importableItems).forEach(key => {
          this.importableItems[key] = res.importableItems[key];
        });

        let csvItems = res.sampleCsvItems
          .map(item => new SampleCsvItem(item.columnNo, item.name, item.sampleData));
        this.sampleCsvItems(csvItems);

        let layouts = (<dto.LayoutDto[]>(res.layouts)).map(l => Layout.fromDto(l));
        this.layouts(layouts);

        dfd.resolve();
      });

      return dfd.promise();
    }

    
		canSave = ko.computed(() => !nts.uk.ui.errors.hasError());

    /**
     * 登録する
     */
    save() {
      //clickSave
    }

    /**
     * 受入対象ドメインを追加する
     */
    addTargetDomain(): JQueryPromise<any> {

      let dfd = $.Deferred();
      let targetDomainId = this.selectedIdInAllDomains();
      
      if (_.some(this.targetDomains(), d => d.domainId === targetDomainId)) {
				ui.dialog.info("既に追加されています");
				return;
      }

      request.ajax("exio/input/importableitem/domain/" + targetDomainId).done(items => {

        this.importableItems[targetDomainId] = items;

        let newLayout = Layout.createDefault(targetDomainId, items);
        this.layouts.push(newLayout);

        this.selectedIdInTargetDomains("" + targetDomainId);

        dfd.resolve();
      });

      return dfd.promise();
    }

    /**
     * 受入対象ドメインを除外する
     */
    removeTargetDomain() {
      if (!this.isSelectedTargetDomain()) {
        return;
      }

      let domainId = this.selectedLayout().domainId;
      this.selectedIdInTargetDomains(undefined);
      
      let newLayouts = this.layouts().filter(l => l.domainId !== domainId);
      this.layouts(newLayouts);
    }

    /**
     * レイアウトに受入項目を追加する
     */
    addItem() {
      
      let domainId = this.selectedTargetDomain().domainId;

      ui.windows.setShared('CMF001DParams', {
        domainId: domainId,
        selectedItems: this.selectedLayout().items().map(i => i.itemNo),
      }, true);

      ui.windows.sub.modal("/view/cmf/001/d/index.xhtml").onClosed(() => {
        if(!ui.windows.getShared('CMF001DCancel')){
          let addingItems: LayoutItem[] = (<string[]>(ui.windows.getShared('CMF001DOutput')))
            .map(n => Number(n))
            .map(itemNo => LayoutItem.createDefault(domainId, itemNo));
          this.selectedLayout().addItems(addingItems);
        }
      });
    }

    gotoDetailSetting() {
      request.jump("../c/index.xhtml", {
				settingCode: this.settingCode,
				domainId: this.selectedLayout().domainId,
				screenId: 'cmf001f'
			});
    }
  }

  function vm(): ViewModel {
    return <ViewModel>(ui._viewModel.content);
  }

  interface DomainIdEnum {
    value: DomainId;
    name: string;
  }

  class ImportableDomain {
    domainId: DomainId;
    domainName: string;

    constructor(domainId: DomainId) {
      this.domainId = domainId;
      this.domainName = __viewContext.enums.ImportingDomainId.find(e => e.value === domainId).name;
    }
  }

  class Layout {

    domainId: DomainId;

    items: KnockoutObservableArray<LayoutItem>;

    constructor(domainId: DomainId, items: LayoutItem[]) {
      this.domainId = domainId;
      this.items = ko.observableArray(items);
    }

    static fromDto(dto: dto.LayoutDto): Layout {
      return new Layout(dto.domainId, dto.items.map(i => LayoutItem.fromDto(dto.domainId, i)));
    }

    static createDefault(domainId: DomainId, items: any[]) {
      return new Layout(
        domainId,
        items.map(i => LayoutItem.createDefault(domainId, i.itemNo)));
    }

    addItems(items: LayoutItem[]) {
      let newItems = this.items();
      items.forEach(i => newItems.push(i));
      newItems.sort((a, b) => a.itemNo - b.itemNo);
      this.items(newItems);
    }

    removeItem(item: LayoutItem) {
      this.items.remove(item);
    }
  }

  class LayoutItem {

    itemNo: ItemNo;
    name: string;
    canDelete: boolean;
    sourceType: KnockoutObservable<SourceType>;
    csvColumnNo: KnockoutObservable<CsvColumnNo>;

    csvSampleData: KnockoutComputed<string>;

    constructor(itemNo: ItemNo, name: string, isOptional: boolean, sourceType: SourceType, csvColumnNo: CsvColumnNo) {
      this.itemNo = itemNo;
      this.name = name;
      this.canDelete = isOptional;
      this.sourceType = ko.observable(sourceType);
      this.csvColumnNo = ko.observable(csvColumnNo);

      this.csvSampleData = ko.computed(() => this.loadCsvSampleData());

      this.sourceType.subscribe(() => this.autoSelectCsvItem());
    }

    loadCsvSampleData() {
      let columnNo = this.csvColumnNo();
      let csvItem = vm().sampleCsvItems().filter(c => c.columnNo == columnNo)[0];
      return (csvItem === undefined) ? "" : csvItem.sampleData;
    }

    autoSelectCsvItem() {
      if (this.sourceType() === SourceType.Fixed) return;

      // すでに何かを選択しているなら何もしない
      if (this.csvColumnNo() !== null && this.csvColumnNo() !== undefined) return;

      let csvItem = vm().sampleCsvItems().filter(c => c.name === this.name)[0];
      if (csvItem !== undefined) {
        this.csvColumnNo(csvItem.columnNo);
      }
    }

    removeThis() {
      vm().selectedLayout().removeItem(this);
    }

    static fromDto(domainId: DomainId, dto: dto.LayoutItemDto): LayoutItem {
      let sourceType = dto.fixedValue ? SourceType.Fixed : SourceType.Csv;
      return LayoutItem.create(domainId, dto.itemNo, sourceType, dto.csvColumnNo);
    }

    static createDefault(domainId: DomainId, itemNo: ItemNo) {
      return LayoutItem.create(domainId, itemNo, SourceType.Fixed, undefined);
    }

    static create(domainId: DomainId, itemNo: ItemNo, sourceType: SourceType, csvColumnNo: CsvColumnNo) {
      let item = vm().importableItems[domainId].filter(i => i.itemNo === itemNo)[0];
      return new LayoutItem(itemNo, item.itemName, item.optional, sourceType, csvColumnNo);
    }
  }

  class SampleCsvItem {
    columnNo: CsvColumnNo;
    name: string;
    sampleData: string;

    constructor(columnNo: CsvColumnNo, name: string, sampleData: string) {
      this.columnNo = columnNo;
      this.name = name;
      this.sampleData = sampleData;
    }
  }

  module dto {

    export interface LayoutDto {
      domainId: DomainId;
      items: LayoutItemDto[];
    }
      
    export interface LayoutItemDto {
      itemNo: ItemNo;
      fixedValue: boolean;
      csvColumnNo: CsvColumnNo;
    }

    export interface ImportableItemDto {
      itemNo: ItemNo;
      itemName: string;
      optional: boolean;
    }
  }
}