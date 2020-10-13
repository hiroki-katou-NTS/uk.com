/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.d {

  // URL API backend
  const API = {
    // ...
  }

  @bean()
  export class ScreenModel extends ko.ViewModel {
    toppagePartCode: KnockoutObservable<string> = ko.observable(null);
    toppagePartName: KnockoutObservable<string> = ko.observable(null);

    mounted() {
      const vm = this;
      // Init dragable item
      $(".menu-creation-option").draggable({
        appendTo: '#menu-creation-layout',
        helper: "clone",
        start: (event, ui) => {
          ui.helper.addClass("menu-creation-item");
        },
        drag: (event, ui) => {
          vm.renderHoveringItem(ui);
        },
        stop: (event, ui) => {

        },
      });
      // Init dropable layout
      $('#menu-creation-layout').droppable({
        accept: ".menu-creation-item",
        drop: (event, ui) => {

        },
      });
    }

    private renderHoveringItem(item: JQueryUI.DraggableEventUIParams) {
      console.log(item);
    }

    public addNewItem() {
      // $('#menu-creation-layout').append(`<div class="menu-creation-item"></div>`);
      // $(".menu-creation-item").draggable({
      //   appendTo: '#menu-creation-layout',
      //   containment: '#menu-creation-layout',
      //   start: (event, ui) => {

      //   },
      //   stop: (event, ui) => {

      //   },
      // });
    }

    public test() {

    }

  }

}