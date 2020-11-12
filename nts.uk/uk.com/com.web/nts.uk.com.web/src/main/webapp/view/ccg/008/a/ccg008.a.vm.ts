module nts.uk.com.view.ccg008.a.viewmodel {
    import commonModel = ccg.model;
    import ntsFile = nts.uk.request.file; 
    import character = nts.uk.characteristics;
    const MINUTESTOMILISECONDS = 60000; 
    export class ScreenModel {
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        flowmenu: KnockoutObservable<model.Placement>;
        visibleMyPage: KnockoutObservable<boolean>;
        dataSource: KnockoutObservable<model.LayoutAllDto>;
        topPageCode: KnockoutObservable<string>;
        isStart: boolean;
        dateSwitch: KnockoutObservableArray<any>;
        selectedSwitch: KnockoutObservable<any>;
        switchVisible: KnockoutObservable<boolean>;
        isVisiableContentF1: KnockoutObservable<boolean> = ko.observable(true);
        isVisiableContentF2: KnockoutObservable<boolean> = ko.observable(true);
        isVisiableContentF3: KnockoutObservable<boolean> = ko.observable(true);
        contentF1: JQuery;
        contentF2: JQuery;
        contentF3: JQuery;
        isShowClosure: KnockoutObservable<boolean> = ko.observable(false); 
        closureSelected: KnockoutObservable<number> = ko.observable(1);
        lstClosure: KnockoutObservableArray<model.ItemCbbModel> = ko.observableArray([]);
        reloadInterval: KnockoutObservable<number> = ko.observable(0);
        lstWidgetLayout2: KnockoutObservableArray<any> = ko.observableArray([]);
        lstWidgetLayout3: KnockoutObservableArray<any> = ko.observableArray([]);
        isShowUrlLayout1: KnockoutObservable<boolean> = ko.observable(false);
        urlIframe1: KnockoutObservable<string> = ko.observable('');
        lstHtml: KnockoutObservableArray<string> = ko.observableArray([]);
        topPageSetting: any;
        layoutType: KnockoutObservable<number> = ko.observable(0);
        isShowSwitch: KnockoutObservable<boolean> = ko.observable(false);
        isShowButtonRefresh: KnockoutObservable<boolean> = ko.observable(false);
        isShowButtonSetting: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            var self = this;
            self.isStart = true;
            self.topPageCode = ko.observable('');
            self.dataSource = ko.observable(null);
            self.visibleMyPage = ko.observable(true);
            self.flowmenu = ko.observable(null);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("CCG008_1"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("CCG008_4"), content: '.tab-content-2', enable: ko.observable(true), visible: self.visibleMyPage }
            ]);
            var transferData = __viewContext.transferred.value;
            var code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
            var fromScreen = transferData && transferData.screen ? transferData.screen : "other";
            self.selectedTab = ko.observable(null);
            self.dateSwitch = ko.observableArray([
                                                    { code: '1', name: nts.uk.resource.getText('CCG008_14')},
                                                    { code: '2', name: nts.uk.resource.getText('CCG008_15')}
                                                ]);
            self.selectedSwitch = ko.observable(null);
            self.switchVisible = ko.observable(true);
            
            // ver4 current month or next month
            self.selectedSwitch.subscribe(function(value){
                character.save('cache', new model.Cache(self.closureSelected(), value));
                nts.uk.ui.windows.setShared('cache', new model.Cache(self.closureSelected(), value));
                var transferData = __viewContext.transferred.value;
                var fromScreen = transferData && transferData.screen ? transferData.screen : "other";
            });
            
            self.closureSelected.subscribe(function(value){
                self.selectedSwitch.valueHasMutated();
            });
            self.reloadInterval.subscribe((data: any) => {
              let miliSeconds: number;
              let minutes: number;
              minutes = self.getMinutes(data);
              miliSeconds = minutes * MINUTESTOMILISECONDS;
              if(data !== 0) {
                setInterval(() => {
                  self.callApiTopPage(self);
                }, miliSeconds);
              }
            })
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            var transferData = __viewContext.transferred.value;
            var code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
            var fromScreen = transferData && transferData.screen ? transferData.screen : "other";
          service.getLoginUser().done(user => {
            service.getSetting().done(res => {
              if(res.reloadInterval){
                self.reloadInterval(res.reloadInterval);
                
              }
              if(user || res) {
                self.isShowButtonSetting(true);
              }
              self.topPageSetting = res;
              //var fromScreen = "login"; 
              if(fromScreen == "login"){
                service.getCache().done((data: any) => {
                    character.save('cache', data).done(() => {
                        self.topPageCode(code);
                        character.restore('cache').done((obj: any)=>{
                            if(obj){
                                let endDate = moment.utc(obj.endDate);
                                if(endDate.add(self.topPageSetting.switchingDate).isSameOrAfter(new Date)){
                                  self.selectedSwitch(1);
                                } else {
                                  self.selectedSwitch(2);
                                }
                                self.closureSelected(obj.closureId)
                                nts.uk.ui.windows.setShared('cache', obj);
                            }else{
                                self.closureSelected(1);
                                self.selectedSwitch(null);
                            }
                        }); 
                    });
                });  
              }else{
                // get combobox and switch button
                character.restore('cache').done((obj: any)=>{
                    if(obj){
                            if(obj.currentOrNextMonth){
                                self.selectedSwitch(obj.currentOrNextMonth);
                            }else{
                                self.selectedSwitch(null);    
                            }
                            self.closureSelected(obj.closureId)
                            nts.uk.ui.windows.setShared('cache', obj);
                    }else{
                        self.closureSelected(1);
                        self.selectedSwitch(null);
                    }
                });    
              }
              let param = {
                topPageSetting: self.topPageSetting,
                fromScreen: fromScreen,
                topPageCode: code
              }
              service.getTopPage(param).then((data: DataTopPage) => {
                self.getToppage(data);
                self.layoutType(data.displayTopPage.layoutDisplayType);
                $( ".content-top" ).resizable();
                self.contentF1 = $('#F1');
                self.contentF2 = $('#F2');
              if (self.layoutType() === LayoutType.LAYOUT_TYPE_0) {
                self.isVisiableContentF2(false);
                self.isVisiableContentF3(false);
              } else if (self.layoutType() === LayoutType.LAYOUT_TYPE_1) {
                self.isVisiableContentF3(false);
                let tcontentF1 = self.contentF1.clone();
                let tcontentF2 = self.contentF2.clone();

                if(!self.contentF2.is(':empty')) {
                  self.contentF1.replaceWith(tcontentF2);
                  self.contentF2.replaceWith(tcontentF1);
                }
              } else if (self.layoutType() === LayoutType.LAYOUT_TYPE_2) {
                self.isVisiableContentF3(false);
              }
              })
              
            });
        })
            
          // 会社の締めを取得する - Lấy closure company
          service.getClosure().done((data: any) => {
            self.lstClosure(data);
              service.getTopPageByCode(fromScreen, self.topPageCode()).done((data: model.LayoutAllDto) => {
                self.dataSource(data);
                dfd.resolve();
            });
          });
          self.isStart = false;
          return dfd.promise();
        }

        callApiTopPage(self: any){
          var transferData = __viewContext.transferred.value;
          var code = transferData && transferData.topPageCode ? transferData.topPageCode : "";
          var fromScreen = transferData && transferData.screen ? transferData.screen : "other";
          let topPageSetting: any;
          service.getSetting().done(res => {
            topPageSetting = res;
            let param = {
              topPageSetting: topPageSetting,
              fromScreen: fromScreen,
              topPageCode: code
            }
            service.getTopPage(param).then((data: DataTopPage) => {
              self.getToppage(data);
            })
          });
        }

        onClickReload(){
          let self = this;
          this.callApiTopPage(self);
        }

        getToppage(data: DataTopPage){
          let self = this;
          let origin: string = window.location.origin;
          if(self.layoutType() !== 0 && data.displayTopPage.layout2) {
            self.isShowButtonRefresh(true)
          }
            if(self.topPageSetting.menuClassification !== MenuClassification.TopPage) {
              if (data.standardMenu.url) {// show standardmenu
                var res = "/" + data.standardMenu.url.split("web/")[1];
                var topPageUrl = "/view/ccg/008/a/index.xhtml";
                if (res && topPageUrl != res.trim()) { 
                    if (_.includes(data.standardMenu.url, ".at.")) { 
                        nts.uk.request.jump("at", res);
                    } else {
                        nts.uk.request.jump(res);
                    } 
                }
              } 
              // show toppage
            } else if (self.topPageSetting.menuClassification === MenuClassification.TopPage) {
              let layout1 = data.displayTopPage.layout1;
              let layout2 = data.displayTopPage.layout2;
              let layout3 = data.displayTopPage.layout3;
              if (layout1) {
                if(data.displayTopPage.urlLayout1){
                  self.isShowUrlLayout1(true);
                  self.urlIframe1(data.displayTopPage.urlLayout1);
                } else {
                  let lstFileId = ko.observableArray([]);
                    _.each(data.displayTopPage.layout1, (item: any) => {
                      let fileId = item.fileId;
                      lstFileId().push(fileId);
                    });
                    let param = {
                      lstFileId: lstFileId()
                    }
                    service.extractFile(param).then((res: any) => {
                      let mappedList: any =
                        _.map(res, (item:any) => {
                          let width = item.htmlContent.match(/(?<=width: )[0-9A-Za-z]+(?=;)/)[0];
                          let height = item.htmlContent.match(/(?<=height: )[0-9A-Za-z]+(?=;)/)[0];
                            return { html: `<iframe style="width: ${width}; height: ${height};" srcdoc='${item.htmlContent}'></iframe>`};
                        });
                      self.lstHtml(mappedList);
                    })
                }
              }
              let dataLayout2: any;
              let dataLayout3: any;
              if (layout2) {
                _.each(layout2, (item: WidgetSettingDto) => {
                  if(item.widgetType === 0 || item.widgetType === 1 || item.widgetType === 2 || item.widgetType === 3 || item.widgetType === 4 ){
                    self.isShowSwitch(true)
                  }
                  if(item.widgetType === 1) {
                    self.isShowClosure(true);
                  }
                  let itemLayout: any;
                  itemLayout.url = origin + self.getUrl(item.widgetType);
                  itemLayout.html = `<iframe src=  ${itemLayout.url}/>`; 
                  itemLayout.order = item.order;
                  dataLayout2.push(itemLayout);
                });
                dataLayout2 = _.orderBy(dataLayout2, ["order"], ["asc"]);
                self.lstWidgetLayout2(dataLayout2);
              }
              
              if (layout3) {
                _.each(layout3, (item: WidgetSettingDto) => {
                  if(item.widgetType === 0 || item.widgetType === 1 || item.widgetType === 2 || item.widgetType === 3 || item.widgetType === 4 ){
                    self.isShowSwitch(true)
                  }
                  if(item.widgetType === 1) {
                    self.isShowClosure(true);
                  }
                  let itemLayout: any;
                  itemLayout.url = origin + self.getUrl(item.widgetType)
                  itemLayout.html = `<iframe src=  ${itemLayout.url}/>`; 
                  itemLayout.order = item.order;
                  dataLayout3.push(itemLayout);
                });
                dataLayout3 = _.orderBy(dataLayout3, ["order"], ["asc"]);
                self.lstWidgetLayout3(dataLayout3);
              }
            }
        }


        openScreenE() {
          let self = this;
          nts.uk.ui.windows.setShared('DataFromScreenA',self.reloadInterval());
          nts.uk.ui.windows.sub.modal("/view/ccg/008/e/index.xhtml").onClosed(() => {
            var result = nts.uk.ui.windows.getShared('DataFromScreenE');
            self.reloadInterval(result);
          });
        }

        getUrl(type: any){
          switch(type) {
            case 0:
              return '/nts.uk.at.web/view/ktg/005/a/index.xhtml';
            case 1:
              return '/nts.uk.at.web/view/ktg/001/a/index.xhtml';
            case 2:
              return '/nts.uk.at.web/view/ktg/004/a/index.xhtml';
            case 3:
              return '/nts.uk.at.web/view/ktg/026/a/index.xhtml';
            case 4:
              return '/nts.uk.at.web/view/ktg/027/a/index.xhtml';
            case 5:
              return '/nts.uk.at.web/view/kdp/001/a/index.xhtml';
            case 6:
              return '/nts.uk.at.web/view/ktg/031/a/index.xhtml';
            case 7: 
              return '/view/ccg/005/a/index.xhtml';
          }
        }
        getMinutes(value: number) {
          switch(value) {
            case 0:
              return 0;
            case 1:
              return 1;
            case 2:
              return 5;
            case 3:
              return 10;
            case 4:
              return 20;
            case 5:
              return 30;
            case 6:
              return 40;
            case 7: 
              return 50;
            case 8:
              return 60;
          }
        }
        
    }
    export module model {
        /** Client Placement class */
        export class Placement {
            // Required
            placementID: string;
            name: string;
            row: number;
            column: number;
            width: number;
            height: number;
            // External Url info
            isExternalUrl: boolean;
            url: string;
            // TopPagePart info
            topPagePartID: string;
            partType: number;
            html: string;
            constructor(placementID: string, name: string, row: number, column: number, width: number, height: number, url?: string, topPagePartID?: string, topPageCode?: string, partType?: number, html: string) {
                // Non Agruments
                this.isExternalUrl = (partType===4) ? true : false;
                this.placementID = placementID;
                this.name = (this.isExternalUrl) ? "外部URL" : name;
                this.row = nts.uk.ntsNumber.getDecimal(row, 0);
                this.column = nts.uk.ntsNumber.getDecimal(column, 0);
                this.width = nts.uk.ntsNumber.getDecimal(width, 0);
                this.height = nts.uk.ntsNumber.getDecimal(height, 0);
                let origin: string = window.location.origin;
                if(partType === 0){
                    if(topPageCode === "0001"){
                        this.url = origin + "/nts.uk.at.web/view/ktg/001/a/index.xhtml";
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }else if(topPageCode === "0002"){
                        this.url = origin + "/nts.uk.at.web/view/ktg/002/a/index.xhtml"; 
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }else if(topPageCode === "0003"){
                        this.url = origin + "/nts.uk.at.web/view/ktg/027/a/index.xhtml"; 
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }else if(topPageCode === "0004"){
                        this.url = origin + "/nts.uk.at.web/view/ktg/030/a/index.xhtml"; 
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }else if(topPageCode === "0005"){
                        this.url = origin + "/nts.uk.at.web/view/kdp/001/a/index.xhtml"; 
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }else if(topPageCode === "0006"){
                        this.url = origin + "/nts.uk.com.web/view/ktg/031/a/index.xhtml"; 
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }else if(topPageCode === "9999"){
                        this.url = origin + "/nts.uk.hr.web/view/jcg/004/a/index.xhtml"; 
                        this.html = '<iframe src="' + this.url + '"/>'; 
                    }
                }else if(partType === 1){
                    this.url = origin + "/nts.uk.at.web/view/ktg/029/a/index.xhtml?code="+topPageCode;
                    this.html = '<iframe src="' + this.url + '"/>';  
                }else if(partType === 4){
                    this.url = url;
                    this.html = '<iframe src="' + this.url + '"/>';
                }else{
                    this.url = url;
                    this.html = html;
                }
                this.topPagePartID = topPagePartID;
                this.partType = partType;
                
            }
        }
        
        export class ItemCbbModel {
            closureId: number;
            closureName: string;
            constructor(closureId: number, closureName: string) {
                this.closureId = closureId;
                this.closureName = closureName;
            }
        }
        
        export class Cache {
            closureId: number;
            currentOrNextMonth: number;
            constructor(closureId: number, currentOrNextMonth: number) {
                this.closureId = closureId;
                this.currentOrNextMonth = currentOrNextMonth;
            }
        }
        /** Server LayoutDto */
        export interface LayoutDto {
            companyID: string;
            layoutID: string;
            pgType: number;
            placements: Array<PlacementDto>;
        }
        /** Server PlacementDto */
        export interface PlacementDto {
            companyID: string,
            placementID: string;
            layoutID: string;
            column: number;
            row: number;
            placementPartDto: PlacementPartDto;
        }
        /** Server PlacementPartDto */
        export interface PlacementPartDto {
            companyID: string;
            width: number;
            height: number;
            topPagePartID?: string;
            code?: string;
            name?: string;
            "type"?: number;
            externalUrl?: string;
        }
        export interface LayoutForMyPageDto {
            employeeID: string;
            layoutID: string;
            pgType: number;
            flowMenu: Array<FlowMenuPlusDto>;
            placements: Array<PlacementDto>;
        }
        export interface LayoutForTopPageDto {
            companyID: string;
            layoutID: string;
            pgType: number;
            flowMenu: Array<FlowMenuPlusDto>;
            placements: Array<PlacementDto>;
            standardMenuUrl: string;
        }
        export interface FlowMenuPlusDto {
            widthSize: number;
            heightSize: number;
            toppagePartID: string;
            fileID: string;
            fileName: string;
            fileType: string;
            mimeType: string;
            originalSize: number;
            storedAt: string;
            row: number;
            column: number;
        }
        export interface LayoutAllDto {
            /**my page*/
            myPage: LayoutForMyPageDto;
            /**top page*/
            topPage: LayoutForTopPageDto;
            /**check xem hien thi toppage hay mypage truoc*/
            check: boolean;//check = true (hien thi top page truoc)||check = false (hien thi my page truoc)
            /**check my page co duoc hien khong*/
            checkMyPage: boolean;
            //check top page co duoc setting khong
            checkTopPage: boolean;
        }
        export enum TopPagePartType {
            STANDARD_WIDGET = 0,
            OPTIONAL_WIDGET =1,
            DASHBOARD = 2,
            FLOWMENU = 3,
            EXTERNAL_URL = 4
        }
        export const MYPAGE = 'mypage';
        export const TOPPAGE = 'toppage';
    }

    enum LayoutType {
      LAYOUT_TYPE_0 = 0,
      LAYOUT_TYPE_1 = 1,
      LAYOUT_TYPE_2 = 2,
      LAYOUT_TYPE_3 = 3,
    }

    export class DataTopPage {
      displayTopPage: DisplayInTopPage;
      menuClassification: number;
      standardMenu: StandardMenuDto;
      constructor(init?: Partial<DataTopPage>) {
        $.extend(this, init);
      }
    }

    export class DisplayInTopPage {
      layout1: Array<FlowMenuOutputCCG008>;
      layout2: Array<WidgetSettingDto>;
      layout3: Array<WidgetSettingDto>;
      urlLayout1: string;
      layoutDisplayType: number;
      constructor(init?: Partial<DisplayInTopPage>) {
        $.extend(this, init);
      }
    }

    export class WidgetSettingDto {
      widgetType: number;
      order: number;
      constructor(init?: Partial<WidgetSettingDto>) {
        $.extend(this, init);
      }
    }
    
    export class FlowMenuOutputCCG008 {
      flowCode: string;
      flowName: string;
      fileId: string;
      constructor(init?: Partial<FlowMenuOutputCCG008>) {
        $.extend(this, init);
      }
    }

    export class StandardMenuDto {
      companyId: string;
      code: string;
      targetItems: string;
      displayName: string;
      displayOrder: number;
      menuAtr: number;
      url: string;
      system: number;
      classification: number;
      webMenuSetting: number;
      afterLoginDisplay: number;
      logLoginDisplay: number;
      logStartDisplay: number;
      logUpdateDisplay: number;
      logSettingDisplay: LogSettingDisplayDto;
      constructor(init?: Partial<StandardMenuDto>) {
        $.extend(this, init);
      }
    }
    export class LogSettingDisplayDto {
      logLoginDisplay: number;
      logStartDisplay: number;
      logUpdateDisplay: number;
    }

    export enum MenuClassification { 
      /**0:標準 */
      Standard = 0, 
      /**1:任意項目申請 */
      OptionalItemApplication = 1, 
      /**2:携帯 */
      MobilePhone = 2, 
      /**3:タブレット */
      Tablet = 3,
      /**4:コード名称 */ 
      CodeName = 4,
      /**5:グループ会社メニュー */
      GroupCompanyMenu  = 5,
      /**6:カスタマイズ */
      Customize = 6,
      /**7:オフィスヘルパー稟議書*/
      OfficeHelper = 7,
      /**8:トップページ*/
      TopPage = 8,
      /**9:スマートフォン*/
      SmartPhone = 9
   }; 
}
