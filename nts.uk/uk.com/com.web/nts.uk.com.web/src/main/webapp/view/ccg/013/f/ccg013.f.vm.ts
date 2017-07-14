module nts.uk.com.view.ccg013.f.viewmodel {
    import windows = nts.uk.ui.windows;
    export class ScreenModel {
        //date editor
        date: KnockoutObservable<string>;
        dataCombobox: Array<any> = [];
        //list
        items: KnockoutObservableArray<TitleMenu>;
        listJobTitle: KnockoutObservableArray<any>;
        isHeaderScroll: KnockoutObservable<boolean>;
        listJobId: KnockoutObservableArray<String>;
        listTitleTying: KnockoutObservableArray<JobTitleTying>;
        listEntity: KnockoutObservableArray<Entity>;
        //combobox
        comboWebMenuCode: KnockoutObservableArray<WebMenu>;
        constructor() {
            let self = this;
            self.items = ko.observableArray([]);
            self.listJobTitle = ko.observableArray([]);
            self.date = ko.observable(new Date().toISOString());
            self.comboWebMenuCode = ko.observableArray([]);
            self.listJobId = ko.observableArray([]);
            self.listTitleTying = ko.observableArray([]);
            self.listEntity = ko.observableArray([]);
            self.isHeaderScroll = ko.computed(function() {
                return self.items().length > 9 ? true : false;
            });
        }

        /** determine data for combobox */
        getListCombobox() {
            let self = this;
            service.findWebMenuCode(self.listJobId()).done(function(listTitleTying) {
                self.listTitleTying(listTitleTying);
                // Set up general object
                self.matchingData();
            });
        }

        /** get data when start dialog */
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            var data = windows.getShared("CCG013F_JOB_TITLE");
            self.dataCombobox = data;
            if (data.length > 0) {
                self.comboWebMenuCode.push(new WebMenu('000', '未設定'));
                _.each(data, function(obj) {
                    self.comboWebMenuCode.push(new WebMenu(obj.webMenuCode, obj.webMenuName));
                });
            }
            // Get list jobtitle
            service.getAllJobTitle(self.date()).done(function(listJobTitle: Array<viewmodel.TitleMenu>) {
                listJobTitle = _.orderBy(listJobTitle, ["code"], ["asc"]);
                _.each(listJobTitle, function(obj: viewmodel.TitleMenu) {
                    self.listJobTitle.push(new TitleMenu(obj.code, obj.name, obj.id, obj.startDate, obj.endDate));
                    self.listJobId.push(obj.id);
                });
                // get list for combobox
                self.getListCombobox();
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            });
            return dfd.promise();
        }

        /** update data when click button register */
        register() {
            let self = this;
            let arr = self.listEntity();
            let data = [];
            let obj;
            _.each(arr, function(item) {
                if(item.webMenuCode()=='000')
                    item.webMenuCode('');
                obj = new JobTitleTying(item.jobId, item.webMenuCode());
                data.push(obj);
            });
            service.updateWebMenuCode(data);
            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){$("#dateTime").focus();});
            
        }

        /** search in list and return objects satisfy condition start date < date input < end date */
        search() {
            let self = this;
            console.log(self.date());
            let arrObj = [];
            service.getAllJobTitle(self.date()).done(function(listJobTitle: Array<viewmodel.TitleMenu>) {
                listJobTitle = _.orderBy(listJobTitle, ["code"], ["asc"]);
                if (listJobTitle.length > 0) {
                    _.each(listJobTitle, function(obj: viewmodel.TitleMenu) {
                        arrObj.push(new TitleMenu(obj.code, obj.name, obj.id, obj.startDate, obj.endDate));
                    });
                }
                self.listJobTitle(arrObj);
                self.matchingData();
            });
        }

        /** close dialog */
        closeDialog() {
            var t0 = performance.now();                
            var t1 = performance.now();
                
            nts.uk.ui.windows.close();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }

        /** Set up general object */
        matchingData() {
            let self = this;
            let arrayTitle = self.listJobTitle();
            let arrayData = self.listTitleTying();
            self.dataCombobox;
            let arrValueObject = [];
            _.each(arrayTitle, function(obj) {
                let titleObj = new TitleMenu(obj.code, obj.name, obj.id, obj.startDate, obj.endDate);
                let dataObj = _.find(arrayData, ['jobId', titleObj.id]);
                let webmenu = null;
                if (!!dataObj) {
                    webmenu = _.find(self.dataCombobox, ['webMenuCode', dataObj.webMenuCode]);
                }
                if (webmenu == undefined) webmenu = new WebMenu("", "");
                arrValueObject.push(new Entity(titleObj.id, titleObj.code, titleObj.name, webmenu.webMenuCode, webmenu.webMenuName));
            });
            self.listEntity([]);
            self.listEntity(arrValueObject);
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
    class Entity {
        jobId: string;
        code: string;
        name: string;
        webMenuCode: KnockoutObservable<string>;
        webMenuName: string;
        selectedCode: KnockoutObservable<string> = ko.observable("");
        constructor(jobId: string, code: string, name: string, webMenuCode: string, webMenuName: string) {
            this.jobId = jobId;
            this.code = code;
            this.name = name;
            this.webMenuCode = ko.observable(webMenuCode);
            this.webMenuName = webMenuName;
            this.selectedCode(this.webMenuCode());
            let self = this;
            this.selectedCode.subscribe(function(value) {
                self.webMenuCode(value);

            });
        }

    }

    export class WebMenu {
        webMenuCode: string;
        webMenuName: string;
        constructor(webMenuCode: string, webMenuName: string) {
            this.webMenuCode = webMenuCode;
            this.webMenuName = webMenuName;
        }
        getWebMenuName(webMenuCode: string) {
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
