/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.d {

  // URL API backend
  const API = {
    // ...
  }

  const CELL_SIZE: number = 40;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $menuCreationLayout: JQuery = null;
    $hoverHighlight: JQuery = null;

    toppagePartCode: KnockoutObservable<string> = ko.observable(null);
    toppagePartName: KnockoutObservable<string> = ko.observable(null);

    mounted() {
      const vm = this;
      // Store creation layout as class variable for easier access
      vm.$menuCreationLayout = $('#menu-creation-layout');
      // Init dragable item
      $(".menu-creation-option").draggable({
        appendTo: '#menu-creation-layout',
        helper: "clone",
        start: (event, ui) => {
          vm.startHoveringItem(ui, 160, 80);
        },
        drag: (event, ui) => {
          vm.renderHoveringItem(ui, 160, 80);
        },
        stop: (event, ui) => {
          vm.createItem(ui, 160, 80);
        },
      });
      // Init dropable layout
      vm.$menuCreationLayout.droppable({
        accept: ".menu-creation-item",
        drop: (event, ui) => {

        },
      });
    }

    /**
     * Start drag item
     * @param item
     * @param width
     * @param height
     */
    private startHoveringItem(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      // Init size + style for dragging item
      item.helper.width(width);
      item.helper.height(height);
      item.helper.addClass("menu-creation-item");
    }

    /**
     * Render hovering highlight effect on drag
     * @param item
     */
    private renderHoveringItem(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      // Parent layout must have position: relative for item.position to be corrected
      const vm = this;
      // Calculate highlight div position
      let positionTop: number = item.position.top > 0 ? Math.round(item.position.top / CELL_SIZE) * CELL_SIZE : 0;
      let positionLeft: number = item.position.left > 0 ? Math.round(item.position.left / CELL_SIZE) * CELL_SIZE : 0;
      // If not existed, create new highlight div
      if (!vm.$hoverHighlight) {
        vm.$hoverHighlight = $("<div>", { id: "item-highlight", "class": "menu-creation-item-highlight" });
      }
      // Set more attr (highlight width, height, position)
      vm.$hoverHighlight.width(width);
      vm.$hoverHighlight.height(height);
      vm.$hoverHighlight.css({ 'top': `${positionTop}px`, 'left': `${positionLeft}px` });
      // Append to creation layout
      vm.$menuCreationLayout.append(vm.$hoverHighlight);
    }

    /**
     * Create new item on drop
     * @param item
     */
    private createItem(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      const vm = this;
      // Calculate new item div position
      let positionTop: number = item.position.top > 0 ? Math.round(item.position.top / CELL_SIZE) * CELL_SIZE : 0;
      let positionLeft: number = item.position.left > 0 ? Math.round(item.position.left / CELL_SIZE) * CELL_SIZE : 0;
      // Create new item div
      const newItem: JQuery = $("<div>", { "class": "menu-creation-item" });
      // Set more attr (highlight width, height, position)
      newItem.width(width);
      newItem.height(height);
      newItem.css({ 'top': `${positionTop}px`, 'left': `${positionLeft}px` });
      // Append to creation layout
      vm.$menuCreationLayout.append(newItem);
    }

    public test() {

    }

  }

}