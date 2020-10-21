/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.f.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {
    isVisiableContentF2: KnockoutObservable<boolean> = ko.observable(true);
    isVisiableContentF3: KnockoutObservable<boolean> = ko.observable(true);

    contentF1: JQuery;
    contentF2: JQuery;
    contentF3: JQuery;
    created(params: any) {
      const vm =this;
      vm.contentF1 = $('#F1');
      vm.contentF2 = $('#F2');

      if (params.selectedId === LayoutType.LAYOUT_TYPE_1) {
        vm.isVisiableContentF2(false);
        vm.isVisiableContentF3(false);
      } else if (params.selectedId === LayoutType.LAYOUT_TYPE_2) {
        vm.isVisiableContentF3(false);
        let tcontentF1 = vm.contentF1.clone();
        let tcontentF2 = vm.contentF2.clone();

        if(!vm.contentF2.is(':empty')) {
          vm.contentF1.replaceWith(tcontentF2);
          vm.contentF2.replaceWith(tcontentF1);
        }
      } else if (params.selectedId === LayoutType.LAYOUT_TYPE_3) {
        vm.isVisiableContentF3(false);
      }
    }

    close() {
      nts.uk.ui.windows.close();
    }
  }

  enum LayoutType {
    LAYOUT_TYPE_1 = 1,
    LAYOUT_TYPE_2 = 2,
    LAYOUT_TYPE_3 = 3,
    LAYOUT_TYPE_4 = 4,
  }
}