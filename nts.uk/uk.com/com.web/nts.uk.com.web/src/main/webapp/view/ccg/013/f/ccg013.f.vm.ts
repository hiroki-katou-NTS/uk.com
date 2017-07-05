module nts.uk.com.view.ccg013.f.viewmodel {
    import windows = nts.uk.ui.windows;
    export class ScreenModel {
        //date editor
        date: KnockoutObservable<string>;

        //list
        items: KnockoutObservableArray<TitleMenu>;
        //        isVisible: KnockoutObservable<boolean>;
        listJobTitle: KnockoutObservableArray<any>;
        comboWebMenuCode: KnockoutObservableArray<WebMenu>;

        //appear/disappear header of scroll on UI
        //        isHeaderScroll: KnockoutObservable<boolean>;
        selectedCode: KnockoutObservable<string> = ko.observable("");
        listJobId: KnockoutObservableArray<String>;
        listTitleTying: KnockoutObservableArray<JobTitleTying>;  
        constructor() {
            let self = this;
            self.items = ko.observableArray([]);
            self.listJobTitle = ko.observableArray([]);
            self.date = ko.observable(new Date().toISOString());
            self.comboWebMenuCode = ko.observableArray([]);
            self.listJobId = ko.observableArray([]);
            self.listTitleTying = ko.observableArray([]);
            //            self.comboItemsAsTopPage = ko.observableArray([]);

            //            self.isHeaderScroll = ko.computed(function() {
            //                return self.items().length > 15 ? true : false;
            //            });
            //            self.start();
        }

        //        start(): any {
        //            let self = this;
        //            let aa = nts.uk.ui.windows.setShared("CCG013F_JOB_TITLE");
        //            if (self.categorySet() == undefined) {
        //                self.startPage();
        //            } else {
        //                self.categorySet(__viewContext.viewModel.viewmodelB.categorySet());
        //            }
        //            $.when(self.findBySystemMenuCls(), self.findDataForAfterLoginDis()).done(function() {
        //                self.searchByDate();
        //            });
        //           
        // get data number "value" in list
        getListStandardMenu(value) {

        }

        // get data when start dialog
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            var data = windows.getShared("CCG013F_JOB_TITLE");
            if (data.length > 0) {
                _.each(data, function(obj) {
                    self.comboWebMenuCode.push(new WebMenu(obj.webMenuCode, obj.webMenuName));
                });
            }
            
            
            
            
            // Get List jobtitle
            service.getAllJobTitle(self.date()).done(function(listJobTitle: Array<viewmodel.TitleMenu>) {
                _.each(listJobTitle, function(obj: viewmodel.TitleMenu) {
                    self.listJobTitle.push(new TitleMenu(obj.code, obj.name, obj.id, obj.startDate, obj.endDate));
                    self.listJobId.push(obj.id);
                });
                
                // get list for combobox
                service.findWebMenuCode(self.listJobId()).done(function(listTitleTying: Array<viewmodel.JobTitleTying>){
                     console.log(listTitleTying);
                });
                
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            });



            return dfd.promise();
        }

        // update data when click button register
        register() {
        }

        search() {
            let self = this;
            console.log(self.date());
            service.getAllJobTitle(self.date()).done(function(listJobTitle: Array<viewmodel.TitleMenu>) {
                _.each(listJobTitle, function(obj: viewmodel.TitleMenu) {
                    self.listJobTitle.push(new TitleMenu(obj.code, obj.name, obj.id, obj.startDate, obj.endDate));
                });
            });
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    export class TitleMenu {
        code: string;
        name: string;
        id: string;
        startDate: string;
        endDate: string;

        constructor(code: string, name: string, id: string, startDate: string, endDate: string) {
            this.code = code;
            this.name = name;
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    export class WebMenu {
        webMenuCode: string;
        webMenuName: string;

        constructor(webMenuCode: string, webMenuName: string) {
            this.webMenuCode = webMenuCode;
            this.webMenuName = webMenuName;
        }
        getWebMenuName( webMenuCode: string){
            
        }
    }

    export class JobTitleTying {
        jobId: string;
        webMenuCode: string;
        
        constructor(jobId: string, webMenuCode: string) {
            this.jobId = jobId;
            this.webMenuCode = webMenuCode;
        }
    }
}
