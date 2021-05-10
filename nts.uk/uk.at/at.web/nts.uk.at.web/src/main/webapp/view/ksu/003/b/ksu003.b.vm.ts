module nts.uk.at.view.ksu003.b {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    const Paths = {
        GET_ALL_TASK_PALETTE: "at/schedule/task/taskpalette/getAll",
        GET_TASK_PALETTE_ORGNIZATION: "at/schedule/task/taskpalette/findOne",
        REGISTER_TASK_PALETTE_ORGNIZATION: "at/schedule/task/taskpalette/register",
        DELETE_TASK_PALETTE_ORGNIZATION: "at/schedule/task/taskpalette/remove",
    };

    @bean()
    class Ksu003bViewModel extends ko.ViewModel {
        displayName: KnockoutObservable<string> = ko.observable('');
        selectedPage: KnockoutObservable<number> = ko.observable();
        groupName: KnockoutObservable<string> = ko.observable('');
        textButtonArrDefault: Array<any> = [
            { name: ko.observable(nts.uk.resource.getText("ページ1", ['１'])), id: 0 },
            { name: ko.observable(nts.uk.resource.getText("ページ2", ['２'])), id: 1 },
            { name: ko.observable(nts.uk.resource.getText("ページ3", ['３'])), id: 2 },
            { name: ko.observable(nts.uk.resource.getText("ページ4", ['４'])), id: 3 },
            { name: ko.observable(nts.uk.resource.getText("ページ5", ['５'])), id: 4 }           
        ];

        textButtonArr: KnockoutObservableArray<any> = ko.observableArray([]);

        selectedLinkButton: KnockoutObservable<number> = ko.observable(1);
        contextMenu: Array<any>;
        textName: KnockoutObservable<string> = ko.observable(null);
        tooltip: KnockoutObservable<string> = ko.observable(null);
        dataWorkPairSet: KnockoutObservableArray<any> = ko.observableArray([]);
        tasks: KnockoutObservableArray<any> = ko.observableArray([]);        
        enableDelete: KnockoutObservable<boolean> = ko.observable(true);
        isEditing: KnockoutObservable<boolean> = ko.observable(false);

        targetId: KnockoutObservable<string> = ko.observable('');
        targetUnit: KnockoutObservable<number> = ko.observable();
        page: KnockoutObservable<number> = ko.observable();


        taskPaletteOrgnization: KnockoutObservable<TaskPaletteOrgnization> = ko.observable(new TaskPaletteOrgnization());       
        sourceEmpty: any[] = [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}];
        endStatus: KnockoutObservable<string> = ko.observable("Cancel"); 

        constructor() {
            super();
            let self = this;
            self.contextMenu = [
                { id: "openDialog", text: nts.uk.resource.getText("作業選択"), action: self.openDialogKdl012.bind(self,event)},
                { id: "delete", text: nts.uk.resource.getText("作業削除"), action: self.deleteTask.bind(self,event) }
            ];

            $("#task").bind("getdatabutton", function(evt, data) {
                self.textName(data.text);
                self.tooltip(data.tooltip);
                self.dataWorkPairSet(data.data);
            });

            self.selectedPage.subscribe((value) => {
                self.loadData();
                self.loadDetail(value);
                self.handleClickLinkButton(value - 1);
            });

            self.loadData();
        }

        loadData(): void {
            const self = this; 
            let request = getShared("dataShareKsu003b");        
            self.targetUnit(request.targetUnit);
            self.targetId(request.targetId)

            self.$blockui("invisible");
            self.textButtonArr.removeAll();
            self.textButtonArr(_.clone(self.textButtonArrDefault));
            self.$ajax(Paths.GET_ALL_TASK_PALETTE, request).done((data: Array<ITaskPalette>) => {
                if(data && data.length > 0){                    
                    for(let i=0; i< data.length; i++){
                        if(data[i].page != null) {
                            self.textButtonArr()[data[i].page - 1].name(nts.uk.text.padRight(data[i].name, ' ', 6));
                        }                        
                    }   
                    self.displayName(data[0].displayName);                                   
                }
                self.selectedPage(request.page);

            }).always(() => {
                self.$blockui("hide");
            });
        }

        loadDetail(page?: number): void {
            const self = this;  
            let request = getShared("dataShareKsu003b");
            request.page = page;
            let dataSource: Array<any> = _.clone(self.sourceEmpty);
            self.$blockui("invisible");
            self.isEditing(false);
            self.enableDelete(false);
            self.$ajax(Paths.GET_TASK_PALETTE_ORGNIZATION, request).done((data: ITaskPaletteOrgnization) => {
                if (!_.isNull(data) && !_.isEmpty(data)) {
                    if (data.targetId != null) {
                        self.taskPaletteOrgnization().updateData(data);
                        self.isEditing(true);
                        self.enableDelete(true);
                    } else {
                        self.taskPaletteOrgnization().resetData();
                    }
                    if (!_.isNull(self.taskPaletteOrgnization().keys())) {
                        for (let i = 0; i < self.taskPaletteOrgnization().keys().length; i++) {
                            dataSource.splice(self.taskPaletteOrgnization().keys()[i] - 1, 1, { text: self.taskPaletteOrgnization().taskAbNames()[i], tooltip: self.taskPaletteOrgnization().taskAbNames()[i] });
                        }

                    } else {
                        self.enableDelete(false);
                    }                   
                    self.tasks(dataSource);
                }
            }).always(() => {
                self.$blockui("hide");
            });
            $('input#taskName').focus();
        }
        
        registerOrUpdate(): void {
            const self = this;

            if (!self.condition()) {
                self.$dialog.error({ messageId: 'Msg_2082' });
                return;
            }

            self.$blockui("invisible");
            let command: any = {
                "unit": self.targetUnit(),
                "targetId": self.targetId(),
                "page": self.selectedPage(),
                "name": self.taskPaletteOrgnization().name(),
                "remarks": self.taskPaletteOrgnization().remarks(),
                "position": self.taskPaletteOrgnization().keys(),
                "taskCode": self.taskPaletteOrgnization().taskCodes()
            }

           
            self.$ajax(Paths.REGISTER_TASK_PALETTE_ORGNIZATION, command).done(() => {
                self.$dialog.info({ messageId: 'Msg_15' }).then(() => {
                    self.selectedPage.valueHasMutated();
                });
            }).fail((res) => {
                if (res.messageId == 'Msg_3') {
                    self.selectedPage();
                }
            }).always(() => {
                self.$blockui("hide");
            });
            self.endStatus('Update');
            self.enableDelete(true);
        }

        remove(): void {
            const self = this;
            self.$dialog.confirm({messageId: "Msg_18"}).then((result: 'no' | 'yes') =>{
                self.$blockui("invisible");
                let command: any = {
                    "unit": self.taskPaletteOrgnization().targetUnit(),
                    "targetId": self.taskPaletteOrgnization().targetId(),
                    "page": self.selectedPage()
                }
                
                if(result === 'yes'){
                    self.$ajax(Paths.DELETE_TASK_PALETTE_ORGNIZATION, command).done(() =>{
                        self.$dialog.info({messageId: "Msg_16"}).then(() =>{                                        
                            self.selectedPage.valueHasMutated(); 
                        });          
                        self.endStatus('Update');             
                    }).always(() =>{
                        self.$blockui("hide");
                    });    
                    self.$blockui("hide");                
                }
                if(result === 'no'){
                    self.$blockui("hide");
                    $('input#taskName').focus();
                }
            }); 
        }

       
        clickLinkButton(element?: any, param?: any): void {         
            let self = this, index: number = param();           
            self.selectedPage(index + 1);
            self.selectedPage.valueHasMutated();
            self.selectedLinkButton(index);
            self.handleClickLinkButton(index);      
            $('input#taskName').focus();     
        }

        handleClickLinkButton(index: number): void {            
            nts.uk.ui.errors.clearAll();
            _.find($('#group-link-button a.hyperlink.color-gray'), (a) => {
                $(a).removeClass('color-gray');
            });
            $($('a.hyperlink')[index]).addClass('color-gray');            
        }

        deleteTask(data: any, event: any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();                
            self.tasks().splice(Number($(event)[0].dataset.idx), 1);
            self.taskPaletteOrgnization().keys.splice(Number($(event)[0].dataset.idx), 1);
            self.taskPaletteOrgnization().taskNames.splice(Number($(event)[0].dataset.idx), 1);
            self.taskPaletteOrgnization().taskCodes.splice(Number($(event)[0].dataset.idx), 1);
            self.taskPaletteOrgnization().taskAbNames.splice(Number($(event)[0].dataset.idx), 1);
            dfd.resolve()
            return dfd.promise();           
        }

        openDialogKdl012(data: any, event:any): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let position : number;
            self.textName(data ? data.text : null);
            self.tooltip(data ? data.tooltip : null);

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            let dataShare = getShared("dataShareKsu003b");
            data ? position = Number(data.target.dataset.idx) + 1 : position = Number($(event)[0].dataset.idx) + 1

            let request = {
                isMultiple: false,
                showExpireDate: true,
                workFrameNoSelection: 1,
                referenceDate: moment(dataShare.referenceDate).format("YYYY/MM/DD"),
                selectionCodeList: ['']
            };
            nts.uk.ui.windows.setShared('KDL012Params', request);

            nts.uk.ui.windows.sub.modal("/view/kdl/012/index.xhtml").onClosed(() => {
                let dataFromKdl012 = getShared("KDL012Output");
                if (dataFromKdl012) {
                    self.textName(dataFromKdl012 ? dataFromKdl012 : self.textName());
                    self.taskPaletteOrgnization().keys.push(position);
                    self.taskPaletteOrgnization().taskCodes.push(dataFromKdl012);
                    self.taskPaletteOrgnization().taskNames.push(dataFromKdl012);
                    self.taskPaletteOrgnization().taskAbNames.push(dataFromKdl012);
                    dfd.resolve({ text: self.textName(), tooltip: self.tooltip(), data: dataFromKdl012.data });
                }
            });    
            return dfd.promise();                   
        }

        openPopup(button): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            let positionButton = $(button).data().idx;
            let dt = self.tasks()[positionButton];
            $("#task").trigger("getdatabutton", { text: dt.text, tooltip: dt.tooltip, data: dt.data });
            $("#popup-area").css('visibility', 'visible');
            let buttonWidth = button.outerWidth(true) - 30;
            $("#popup-area").position({ "of": button, my: "left+" + buttonWidth + " top", at: "left+" + buttonWidth + " top" });
            $("#task").bind("namechanged", function(evt, data) {
                $("#task").unbind("namechanged");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    dfd.resolve(data);
                } else {
                    dfd.resolve(button.parent().data("cell-data"));
                }
            });
            return dfd.promise();
        }

        decision(): void {
            let self = this;
            $("#popup-area").css('visibility', 'hidden');
            $("#task").trigger("namechanged", { text: self.textName(), tooltip: self.tooltip(), data: self.dataWorkPairSet() });
        }

        condition(): boolean {
            const self = this;
            if(_.isNull(self.taskPaletteOrgnization().keys()) || _.isEmpty(self.taskPaletteOrgnization().keys())){
                return false;
            }
            return true;
        }

        closePopup(): void {
            nts.uk.ui.errors.clearAll()
            $("#popup-area").css('visibility', 'hidden');
            $("#task").trigger("namechanged", undefined);
        }
        closeDialog(): void {
            const self = this;  
            self.endStatus() === 'Update' ? setShared('dataShareFromKsu003b', self.selectedPage()) :setShared('dataShareSU003B',null);
            self.$window.close();
        }
    }   

    interface ITaskPalette {      
        displayName: string;
        name: string;        
        page: number;
    }

    interface ITaskPaletteOrgnization {
        targetUnit: number;
        targetId: string;
        displayName: string;
        name: string;
        remarks: string;
        page: number;
        keys: Array<number>;
        taskCodes: Array<string>;
        taskNames: Array<string>;
        taskAbNames: Array<string>;
    }

    class TaskPaletteOrgnization {
        targetUnit: KnockoutObservable<number> = ko.observable();
        targetId: KnockoutObservable<string> = ko.observable();
        displayName: KnockoutObservable<string> = ko.observable();
        name: KnockoutObservable<string> = ko.observable();
        remarks: KnockoutObservable<string> = ko.observable();
        page: KnockoutObservable<number> = ko.observable();      
        keys: KnockoutObservableArray<number> = ko.observableArray([]);
        taskCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        taskNames: KnockoutObservableArray<string> = ko.observableArray([]);
        taskAbNames: KnockoutObservableArray<string> = ko.observableArray([]);
        tasks: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

        updateData(param: ITaskPaletteOrgnization){
            const self = this;
            self.targetUnit(param.targetUnit);
            self.targetId(param.targetId);
            self.displayName(param.displayName);
            self.name(param.name);
            self.remarks(param.remarks);
            self.page(param.page);
            self.keys(param.keys);
            self.taskCodes(param.taskCodes);
            self.taskNames(param.taskNames);
            self.taskAbNames(param.taskAbNames);          
        }

        resetData() {
            const self = this;
            // self.targetUnit();
            // self.targetId('');
            // self.displayName();
            self.name('');
            self.remarks('');
            // self.page();
            self.keys([]);
            self.taskCodes([]);
            self.taskNames([]);
            self.taskAbNames([]);  
        }
    }   

    class ItemModel {
        key: string;
        value: string;

        constructor(key: string, value: string) {
            this.key = key;
            this.value = value;
        }
    }
}