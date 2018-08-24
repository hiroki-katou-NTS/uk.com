module nts.uk.at.view.kmk010.c {

    import OutsideOTBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OutsideOTBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    import OutsideOTBRDItemModel = nts.uk.at.view.kmk010.a.viewmodel.OutsideOTBRDItemModel;
    import OutsideOTBRDItemLangDto = nts.uk.at.view.kmk010.a.service.model.OutsideOTBRDItemLangDto;
    
    export module viewmodel {

        export class ScreenModel {
            lstOutsideOTBRDItemModel: OutsideOTBRDItemModel[];
            lstProductNumber: EnumConstantDto[];
            languageId: string;
            static LANGUAGE_ID_JAPAN = 'ja';
            textOvertimeName: KnockoutObservable<string>;
            enableCheckbox: KnockoutObservable<boolean>;

           constructor() {
               var self = this;
               self.lstOutsideOTBRDItemModel = [];
               self.lstProductNumber = [];
               self.languageId = nts.uk.ui.windows.getShared("languageId");
               self.textOvertimeName = ko.observable(nts.uk.resource.getText('KMK010_45'));
               self.enableCheckbox = ko.observable(true);
           }
            /**
             * start page when init data
             */
           public startPage(): JQueryPromise<any> {
               var self = this;
               var dfd = $.Deferred();
               service.findAllProductNumber().done(function(data) {
                   self.lstProductNumber = data;
                   nts.uk.at.view.kmk010.a.service.findAllOutsideOTBRDItem().done(function(data) {
                       self.lstOutsideOTBRDItemModel = [];
                       for (var dto of data) {
                           var model: OutsideOTBRDItemModel = new OutsideOTBRDItemModel();
                           model.updateData(dto, false);
                           model.updateEnableCheck(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN);
                           model.setUpdateData(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN);
                           self.lstOutsideOTBRDItemModel.push(model);
                       }
                       // equal language id 
                       if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) {
                           self.textOvertimeName(nts.uk.resource.getText('KMK010_45'));
                           self.enableCheckbox(true);
                       } else {
                           self.textOvertimeName(nts.uk.resource.getText('KMK010_64'));
                           self.enableCheckbox(false);
                           nts.uk.at.view.kmk010.a.service.findAllOvertimeLanguageBRDItem(self.languageId).done(function(dataLanguageBRDItem) {
                               if (dataLanguageBRDItem && dataLanguageBRDItem.length > 0) {
                                   for (var dataLang of dataLanguageBRDItem) {
                                       for (var model of self.lstOutsideOTBRDItemModel) {
                                           if (model.breakdownItemNo() == dataLang.breakdownItemNo) {
                                               model.name(dataLang.name);
                                           }
                                       }
                                   }
                               } else {
                                   for (var model of self.lstOutsideOTBRDItemModel) {
                                       model.name('');
                                   }
                               }
                               dfd.resolve(self);
                           });
                       }
                       dfd.resolve(self);
                   });
               });

               return dfd.promise();
           }
            /**
             * save overtime break down item on lick button save
             */
           private saveOutsideOTBRDItem(): void {
               var self = this;
              
               if (self.validateDomainSave()) {
                   return;
               }
               // block ui.
               nts.uk.ui.block.invisible();
               if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) {

                   // convert model to dto
                   var breakdownItems: OutsideOTBRDItemDto[] = [];
                   for (var model of self.lstOutsideOTBRDItemModel) {
                       breakdownItems.push(model.toDto());
                   }

                   // call service save all overtime
                   service.saveAllOutsideOTBRDItem(breakdownItems).done(function() {
                       nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                           nts.uk.ui.windows.setShared("isSave", 1);
                           nts.uk.ui.block.clear();
//                           nts.uk.ui.windows.close();
                       });
                   }).fail(function(error) {
                       nts.uk.ui.dialog.alertError(error).then(function() {
                           nts.uk.ui.windows.setShared("isSave", 1);
                           nts.uk.ui.block.clear();
                          // nts.uk.ui.windows.close();
                       });
                   });
               } else {
                   var overtimeLangNames: OutsideOTBRDItemLangDto[] = [];
                   for (var model of self.lstOutsideOTBRDItemModel) {
                       var dto: OutsideOTBRDItemLangDto = {
                           name: model.name(),
                           languageId: self.languageId,
                           breakdownItemNo: model.breakdownItemNo()
                       };
                       overtimeLangNames.push(dto);
                   }
                   // call service save all overtime language name
                   service.saveAllOvertimeLanguageBRDItem(overtimeLangNames).done(function() {
                       nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                           nts.uk.ui.windows.setShared("isSave", 1);
                           nts.uk.ui.block.clear();
//                           nts.uk.ui.windows.close();
                       });
                   }).fail(function(error) {
                       nts.uk.ui.dialog.alertError(error).then(function() {
                           nts.uk.ui.windows.setShared("isSave", 1);
                           nts.uk.ui.block.clear();
//                           nts.uk.ui.windows.close();
                       });

                   });
               }
               self.switchNameResource();
           }
           /**
            * function validate domain save
            */
           private validateDomainSave(): boolean {
               var self = this;
               for (var model of self.lstOutsideOTBRDItemModel) {
                   $('#breakdownItemNo_' + model.breakdownItemNo()).ntsError("clear");
               }
               for (var model of self.lstOutsideOTBRDItemModel) {
                   if (model.requiredText()) {
                       $('#breakdownItemNo_' + model.breakdownItemNo()).ntsEditor("validate");
                   }
               }
               for (var model of self.lstOutsideOTBRDItemModel) {
                   if (model.requiredText() && $('#breakdownItemNo_' + model.breakdownItemNo()).ntsError('hasError')) {
                       return true;
                   }
               }
               return false;

           }
            
            /**
             * function by click button close dialog
             */
           private closeSaveOutsideOTBRDItem(): void {
               nts.uk.ui.windows.close();
           }
            
           /**
             * swtich name for element when change language
             */
            private switchNameResource(): string {
                let self = this;
                
                if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) { 
                    return '#[KMK010_46]';
                } else {
                    return '#[KMK010_64]'; 
                }
            } 
        }

    }
}