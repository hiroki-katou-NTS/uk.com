///<reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.cdl010.test.screenModel {

  @bean()
  export class ViewModel extends ko.ViewModel {

    created() {}

    openDialog() {
      const vm = this;
      const data = {
        employeeId: 'Test',
        referenceDate: '2020-02-02',
      };
      vm.$window.modal('com', '/view/cdl/010/a/index.xhtml', data);
    }
  }
}
