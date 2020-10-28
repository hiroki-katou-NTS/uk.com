/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.j {
  import CCG034D = nts.uk.com.view.ccg034.d;

  const POPUP_ARROW_ID: string = 'J_1';
  const MAXIMUM_IMAGE_COUNT = 4;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    partData: CCG034D.PartDataArrow = null;

    arrowImageList: ItemModel[] = [];

    created(params: any) {
      const vm = this;
      vm.partData = params;
    }

    mounted() {
      const vm = this;
      // Init popup J
      // Generate image list
      for (let i = 0; i < 12; i++) {
        vm.arrowImageList.push({ code: i, name: `../resources/j/CCG034J_${nts.uk.text.padLeft(String(i + 1), '0', 3)}.png` });
      }
      // Adding images inside popup
      for (let i = 0; i < 12; i += MAXIMUM_IMAGE_COUNT) {
        const $listImage: JQuery[] = [];
        for (let j = i; j < i + MAXIMUM_IMAGE_COUNT; j++) {
          $listImage.push(
            $('<img>', {
              'id': `J1_${j}`,
              'class': 'arrow-pic-preview',
              'src': vm.arrowImageList[j].name,
            })
              .on('click', (event) => vm.chooseArrow(event))
              .attr('arrow-id', j));
        }
        $(`#${POPUP_ARROW_ID}`)
          .append($('<div>').append($listImage));
      }
    }

    public chooseArrow(event: JQueryEventObject) {
      const vm = this;
      const itemId: string = $(event.target).attr('arrow-id');
      const item = vm.arrowImageList[Number(itemId)];
      // Update data
      vm.partData.fileName = item.name;
      vm.partData.fileSrc = item.name;
      // Close dialog
      vm.$window.close(vm.partData);
    }

    /**
    * Close dialog
    */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }

  export interface ItemModel {
    code: number;
    name: string;
  }
}