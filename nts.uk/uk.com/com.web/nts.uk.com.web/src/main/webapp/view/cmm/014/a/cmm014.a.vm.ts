module nts.uk.com.view.cmm014.a.viewmodel {
    
    import ClassificationModel = service.model.ClassificationFindDto; 
    import blockUI = nts.uk.ui.block;
        
    export class ScreenModel {
        
        classificationTableColumns: KnockoutObservableArray<any>;//nts.uk.ui.NtsGridListColumn
        
        listClassification: KnockoutObservableArray<ClassificationModel>;
        currentSelectedRow: KnockoutObservable<any>;
        
        currentClassification: KnockoutObservable<viewmodel.model.ClassificationView>;
        
        isEnableDeleteButton: KnockoutObservable<boolean>;
        
        constructor() {
            let _self = this;
            
            _self.classificationTableColumns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 80 }
                ]);
           
            _self.listClassification = ko.observableArray([]);
            _self.currentSelectedRow = ko.observable(null);
              
            _self.currentClassification = ko.observable(new viewmodel.model.ClassificationView(new ClassificationModel(), true));
            
            _self.isEnableDeleteButton = ko.observable(true);
            
            _self.currentSelectedRow.subscribe(function(clfCode) {
                if(clfCode == null){
                    return;
                }else{
                    _self.currentClassification().classificationCode(_self.findObj(clfCode).code);
                    _self.currentClassification().classificationName(_self.findObj(clfCode).name);
                    _self.currentClassification().memo(_self.findObj(clfCode).memo);
                    _self.currentClassification().inputClfCodeEnable(false);
                }
            });
        }
        
        /**
         * find classification in list by code
         */
        public findObj(code: any): any {
            let _self = this;
            var itemModel: ClassificationModel = null;
            _.find(_self.listClassification(), (obj: ClassificationModel) => {
                if (obj.code == code) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }
        
        /**
         * init register
         */
        public initRegisterClassification() {
            let _self = this;
            _self.currentSelectedRow(null);
            _self.currentClassification().refresh();
            _self.isEnableDeleteButton(false);
        }
        
        /**
         * register new classification
         */
        public registerClassification(): void {
            let _self = this;
            blockUI.invisible()
            
            var command = {
                classificationCode: _self.currentClassification().classificationCode(),
                classificationName: _self.currentClassification().classificationName(),
                memo: _self.currentClassification().memo()
            };
            
            _self.saveClassificationInfo(command).done(function(){
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    
                    //reload data in table
                   _self.getClassificationList().done(function(data: Array<ClassificationModel>) {
                        _self.listClassification(data);
                        _self.currentSelectedRow(command.classificationCode);
                        
                        _self.currentClassification().classificationCode(_self.findObj(command.classificationCode).code);
                        _self.currentClassification().classificationName(_self.findObj(command.classificationCode).name);
                        _self.currentClassification().memo(_self.findObj(command.classificationCode).memo);
                        _self.currentClassification().inputClfCodeEnable(false);
                  });
                  _self.isEnableDeleteButton(true);
                });   
                blockUI.clear();
            }).fail(error => {
                if (error.messageId == 'Msg_3') {
                    nts.uk.ui.dialog.info({ messageId: "Msg_3" }).then(function() {
                        $("#empCode").focus();
                    });
                } else {
                    nts.uk.ui.dialog.alertError(error);
                }
                blockUI.clear();
            });
            
        }
        
        /**
         * delete a classification
         */
        public deleteClassification(): void {
            let _self = this;
            // Remove
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                
                let command = {
                    classificationCode: _self.currentClassification().classificationCode()
                };
                blockUI.invisible();
                service.removeClassification(command).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                        // Reload Component
                        _self.getClassificationList().done(function(data: Array<ClassificationModel>) {
                            let index = _self.listClassification().indexOf(_self.findObj(command.classificationCode));
                             _self.listClassification(data);
                            if (index == (_self.listClassification().length)) {
                                _self.currentSelectedRow(_self.listClassification()[index - 1].code);
                            } else {
                                _self.currentSelectedRow(_self.listClassification()[index].code);
                            }
                            
                        });
                    });
                    
                    blockUI.clear();
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError(res.message).then(() => {blockUI.clear();});
                    
                });
            }).ifNo(function() {
                blockUI.clear();
            });
        }
        
        /**
         * prepare data when page started
         */
        public start(): JQueryPromise<any> {
            let _self = this;
            var dfd = $.Deferred<any>();
            
            _self.getClassificationList().done(function(data: Array<ClassificationModel>) {
                _self.listClassification(data);
                _self.currentSelectedRow(data[0].code);
                
                _self.currentClassification().classificationCode(data[0].code);
                _self.currentClassification().classificationName(data[0].name);
                _self.currentClassification().memo(data[0].memo);
                _self.currentClassification().inputClfCodeEnable(false);
                
                dfd.resolve();
            }).fail(function(error: any) {
                alert(error.message);
            })
            
            return dfd.promise();
        }

        private getClassificationList(): JQueryPromise<any> {
            let _self = this;
            var dfd = $.Deferred<Array<ClassificationModel>>();
            service.findAllClassification().done(function(data: Array<ClassificationModel>) {
                dfd.resolve(data);
            }).fail(function() {
                alert('error');
            });
            return dfd.promise();
        }
        
        private saveClassificationInfo(data: any): JQueryPromise<any> {
            let _self = this;
            var dfd = $.Deferred<void>();
            
            service.saveClassification(data).done(function(){
                dfd.resolve(); 
            });
            
            return dfd.promise();
        }
    }     
    
    /**
    * Model namespace.
    */
    export module model {
        
        export class ClassificationView {
            inputClfCodeEnable: KnockoutObservable<boolean>;
            classificationCode: KnockoutObservable<string>;
            classificationName: KnockoutObservable<string>;
            memo: KnockoutObservable<string>;
        
            constructor(classification: ClassificationModel, enable: boolean) {
                this.classificationCode = ko.observable(null);
                this.classificationName = ko.observable(null);
                this.memo = ko.observable(null);
                this.inputClfCodeEnable = ko.observable(enable);
            }
            
            refresh() {
                let _self = this;
                _self.inputClfCodeEnable(true);
                _self.classificationCode("");
                _self.classificationName("");
                _self.memo("");
            }
        }

    }    
}