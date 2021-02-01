/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.b.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    listDataMulti: KnockoutObservableArray<any> = ko.observableArray([]);
    isEnable: KnockoutObservable<boolean> = ko.observable(true);
    isEditable: KnockoutObservable<boolean> = ko.observable(false);
    isRequired: KnockoutObservable<boolean> = ko.observable(false);
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(false);
    columns: KnockoutObservableArray<any>;
    columns2: KnockoutObservableArray<any>;
    selectedValue: KnockoutObservable<string> = ko.observable();
    lstRole: KnockoutObservableArray<any> = ko.observableArray([]);
    lstJobTitle: KnockoutObservableArray<any> = ko.observableArray([]);
    cId: KnockoutObservable<string> = ko.observable("");
    created() {
      const vm = this;
      vm.columns = ko.observableArray([
        { headerText: vm.$i18n("CCG005_5"), key: "name", width: 100 },
        { headerText: 'code', key: "roleCode", hidden: true},
      ]);

      vm.$ajax("com", "screen/com/ccg005/get-permission-settings")
      .then((data:any) => {
        vm.lstRole(data.role);
        const lstdata = data.jobTitle.map(x => {
          if(data.specifyAuthInquiry.includes(x.jobTitleCode)){
            return {flag: true, jobTitleName: x.jobTitleName, jobTitleCode: x.jobTitleCode}
          }
          return {flag: false, jobTitleName: x.jobTitleName, jobTitleCode: x.jobTitleCode}
        })
        vm.lstJobTitle(lstdata);
        $("#grid").ntsGrid({
          width: "175px",
          height: "200px",
          dataSource: vm.lstJobTitle(),
          primaryKey: "id",
          virtualization: true,
          rowVirtualization: true,
          virtualizationMode: "continuous",
          columns: [
            { headerText: "ID", key: "jobTitleCode", dataType: "string", width: "50px" ,hidden: true},
            {
              headerText: vm.$i18n("CCG005_7"),
              key: "flag",
              dataType: "boolean",
              width: "65px",
              ntsControl: "Checkbox",
            },
            {
              headerText: vm.$i18n("CCG005_6"),
              key: "jobTitleName",
              dataType: "string",
              width: "110px",
            }
          ],
          features: [{ name: "Sorting", type: "local" }],
          ntsControls: [
            {
              name: "Checkbox",
              options: { value: 1, text: "" },
              optionsValue: "value",
              optionsText: "text",
              controlType: "CheckBox",
              enable: true,
            }
          ]
        }); 
      });

      
      
    }
    mounted() {
      const vm = this;
     
    }

    onClickCancel() {
      this.$window.close();
    }
    
    onClickSave() {
      const vm = this;
      const cId = __viewContext.user.companyId;
      const listId = vm.lstJobTitle().filter(x => x.flag === true)
      let data: any = {
        cId: cId,
        role: vm.selectedValue(),
        listId: listId
      }
      this.$window.close({
        data
      });
    }
  }
}
