module nts.uk.com.view.ccg015.a {
    export module viewmodel {
        import TopPageItemDto = ccg015.a.service.model.TopPageItemDto;
        import TopPageItemDetailDto = ccg015.a.service.model.TopPageItemDetailDto;
        export class ScreenModel {
            listTopPage : KnockoutObservableArray<Node>;
            toppageSelectedCode : KnockoutObservable<string>;
            topPageModel: KnockoutObservable<TopPageModel>;
            columns : KnockoutObservable<any>;
            isNewMode: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.listTopPage= ko.observableArray<Node>([]);
                self.toppageSelectedCode = ko.observable(null);
                self.topPageModel = ko.observable(new TopPageModel());
                self.columns = ko.observableArray([
                    { headerText: "コード", width: "50px", key: 'code', dataType: "string", hidden: false },
                    { headerText: "名称", width: "300px", key: 'nodeText', dataType: "string" }
                ]);
                self.isNewMode = ko.observable(true);
                self.toppageSelectedCode.subscribe(function(selectedTopPageCode: string) {
                    service.loadDetailTopPage(selectedTopPageCode).done(function(data: TopPageItemDetailDto) {
                        self.loadTopPageItemDetail(data);
                    });
                });
            }
            start(): JQueryPromise<void>
            {
                var self = this;
                var dfd = $.Deferred<void>();
                service.loadTopPage().done(function(data: Array<TopPageItemDto>) {
                    data.forEach(function(item, index) {
                        self.listTopPage.push(new Node(item.topPageCode, item.topPageName,null));
                        dfd.resolve();
                    });
                });
                return dfd.promise();
            }
            
            //load top page Item 
            private loadTopPageItemDetail(data: TopPageItemDetailDto){
                var self =this;
                    self.topPageModel().topPageCode(data.topPageCode);
                    self.topPageModel().topPageName(data.topPageName);
            }
            private collectData():TopPageItemDetailDto{
                    return null;
            }
            private saveTopPage() {
                var self = this;
                //check update or create
                if (self.isNewMode()) {
                    service.registerTopPage(self.collectData()).done(function() {
                        //register success
                    });
                }
                else {
                    service.updateTopPage(self.collectData()).done(function() {
                        //update success
                    });
                }
            }
            private openMyPageSettingDialog(){
                nts.uk.ui.windows.sub.modal("/view/ccg/015/b/index.xhtml", {
                    height: 700, width: 850,
                    title: "マイページの設定",
                    dialogClass: 'no-close'
                }).onClosed(() => {
                    //TODO on Close dialog
                });

            }
            
             private copyTopPage(){
                nts.uk.ui.windows.sub.modal("/view/ccg/015/c/index.xhtml", {
                    height: 350, width: 650,
                    title: "他のトップページコピー",
                    dialogClass: 'no-close'
                }).onClosed(() => {
                    //TODO on Close dialog
                });

            }
            private newTopPage(){
                var self = this;
                self.topPageModel(new TopPageModel());   
            }
            private removeTopPage() {
                var self = this;
                service.deleteTopPage(self.toppageSelectedCode()).done(function() {
                    //delete success
                }).fail();
            }
        }
        export class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: Array<Node>;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = name;
                self.childs = childs;
                self.custom = 'Random' + new Date().getTime();
            }
        }
        export class TopPageModel {
            url: string;
            topPageCode : KnockoutObservable<string>;
            topPageName : KnockoutObservable<string>;
            layout: Layout;//TODO
            constructor()
            {
                this.url;
                this.topPageCode = ko.observable('');
                this.topPageName = ko.observable('');
                this.layout = null;    
            }
        }
        export class Layout{
            
        }
    }
}