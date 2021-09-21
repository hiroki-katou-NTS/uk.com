/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.oew001.c {

  import model = oew001.share.model;

  const API = {
    getEquipmentInfoList: "com/screen/oew001/getEquipmentInfoList",
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    
    selectedEquipmentClsCode: KnockoutObservable<string> = ko.observable("");
    equipmentClsName: KnockoutObservable<string> = ko.observable("");
    equipmentInfoList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedEquipmentInfoCode: KnockoutObservable<string> = ko.observable("");
    yearMonth: KnockoutObservable<string> = ko.observable("");

    // dto
    equipmentClassification: KnockoutObservable<model.EquipmentClassificationDto> = ko.observable(null);
    equipmentInformationList: KnockoutObservableArray<model.EquipmentInformationDto> = ko.observableArray([]);
  }
}
