module nts.uk.at.view.kmk010.c {

    import OvertimeBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OvertimeBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    import OvertimeBRDItemModel = nts.uk.at.view.kmk010.a.viewmodel.OvertimeBRDItemModel;
    import OvertimeLangBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OvertimeLangBRDItemDto;
    
    export module viewmodel {

        export class ScreenModel {
            lstOvertimeBRDItemModel: OvertimeBRDItemModel[];
            lstProductNumber: EnumConstantDto[];
            languageId: string;
            static LANGUAGE_ID_JAPAN = 'ja';
            textOvertimeName: KnockoutObservable<string>;
            enableCheckbox: KnockoutObservable<boolean>;

           constructor() {
               var self = this;
               self.lstOvertimeBRDItemModel = [];
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
               service.findAllProductNumber().done(function(data){
                  self.lstProductNumber = data; 
               });
               nts.uk.at.view.kmk010.a.service.findAllOvertimeBRDItem().done(function(data) {
                   self.lstOvertimeBRDItemModel = [];
                   for (var dto of data) {
                       var model: OvertimeBRDItemModel = new OvertimeBRDItemModel();
                       model.updateData(dto);
                       self.lstOvertimeBRDItemModel.push(model);
                   }
                     // equal language id 
                   if (self.languageId === ScreenModel.LANGUAGE_ID_JAPAN) {
                       self.textOvertimeName(nts.uk.resource.getText('KMK010_45'));
                       self.enableCheckbox(true);
                   } else {
                       self.textOvertimeName(nts.uk.resource.getText('KMK010_64'));
                       self.enableCheckbox(false);
                       nts.uk.at.view.kmk010.a.service.findAllOvertimeLanguageBRDItem(self.languageId).done(function(dataLanguageBRDItem){
                           if (dataLanguageBRDItem && dataLanguageBRDItem.length > 0) {
                               for(var dataLang of dataLanguageBRDItem){
                                    for(var model of self.lstOvertimeBRDItemModel){
                                        if(model.breakdownItemNo() == dataLang.breakdownItemNo){
                                            model.name(dataLang.name);    
                                        }    
                                    }    
                               }
                           }else {
                               for (var model of self.lstOvertimeBRDItemModel) {
                                   model.name('');
                               }
                           }
                       });
                   }
                   dfd.resolve(self);
               });
               return dfd.promise();
           }
            /**
             * save overtime break down item on lick button save
             */
            private saveOvertimeBRDItem(): void{
                var self = this;
                if(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN){
                    // convert model to dto
                    var overtimes: OvertimeBRDItemDto[] = [];
                    for (var model of self.lstOvertimeBRDItemModel) {
                        overtimes.push(model.toDto());
                    }

                    // call service save all overtime
                    service.saveAllOvertimeBRDItem(overtimes).done(function() {
                        console.log('YES');
                    }).fail(function(error) {

                    });
                }else {
                     var overtimeLangNames: OvertimeLangBRDItemDto[] = [];
                    for(var model of self.lstOvertimeBRDItemModel){
                        var dto: OvertimeLangBRDItemDto = {
                            name: model.name(),
                            languageId: self.languageId,
                            breakdownItemNo: model.breakdownItemNo()
                        };
                        overtimeLangNames.push(dto); 
                    }    
                    // call service save all overtime language name
                    service.saveAllOvertimeLanguageBRDItem(overtimeLangNames).done(function(){
                        console.log('YES');
                    }).fail(function(error){
                        
                    });    
                }    
            }
        }

    }
}