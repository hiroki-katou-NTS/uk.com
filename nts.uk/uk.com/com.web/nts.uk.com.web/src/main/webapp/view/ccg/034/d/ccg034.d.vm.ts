/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.d {

  // URL API backend
  const API = {
    // ...
  }

  const MENU_CREATION_LAYOUT_ID = 'menu-creation-layout';
  const ITEM_HIGHLIGHT_ID = 'item-highlight';
  const CELL_SIZE: number = 40;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $menuCreationLayout: JQuery = null;
    $hoverHighlight: JQuery = null;
    layoutSizeText: KnockoutObservable<string> = ko.observable('');

    mounted() {
      const vm = this;
      // Init text resource
      vm.layoutSizeText(vm.$i18n('CCG034_50', ['1920', '1080']));
      // Store creation layout as class variable for easier access
      vm.$menuCreationLayout = $(`#${MENU_CREATION_LAYOUT_ID}`);
      // Init dragable item
      $(".menu-creation-option").draggable({
        appendTo: `#${MENU_CREATION_LAYOUT_ID}`,
        helper: "clone",
        start: (event, ui) => {
          const partSize = vm.getPartSize(ui.helper.attr("data-part-type"));
          vm.startDragItemFromMenu(ui, partSize.width, partSize.height);
        },
        drag: (event, ui) => {
          const partSize = vm.getPartSize(ui.helper.attr("data-part-type"));
          vm.renderHoveringItemOnDrag(ui, partSize.width, partSize.height);
        },
        stop: (event, ui) => {
          vm.removeHoveringItem();
          vm.createItemFromMenu(ui, ui.helper.attr("data-part-type"));
        },
      });
      // Init dropable layout
      vm.$menuCreationLayout.droppable({
        accept: ".menu-creation-item",
      });
    }

    /**
     * Start drag item from menu
     * @param item
     * @param width
     * @param height
     */
    private startDragItemFromMenu(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      // Init size + style for dragging item
      item.helper
        .outerWidth(width)
        .outerHeight(height)
        .addClass("menu-creation-item ui-selected")
        .empty();
    }

    /**
     * Create new item on drop from menu
     * @param item
     */
    private createItemFromMenu(item: JQueryUI.DraggableEventUIParams, partType: string) {
      const vm = this;
      const partSize = vm.getPartSize(partType);
      // Calculate new item div position
      const positionTop: number = item.position.top > 0 ? Math.round(item.position.top / CELL_SIZE) * CELL_SIZE : 0;
      const positionLeft: number = item.position.left > 0 ? Math.round(item.position.left / CELL_SIZE) * CELL_SIZE : 0;
      // Create new item div
      const newItem: JQuery = $("<div>", { "class": "menu-creation-item" });
      newItem
        // Set more attr (highlight width, height, position)
        .outerWidth(partSize.width)
        .outerHeight(partSize.height)
        .css({ 'top': `${positionTop}px`, 'left': `${positionLeft}px` })
        // Update item data object
        .data(new PartData({
          width: partSize.width,
          height: partSize.height,
          minWidth: partSize.width,
          minHeight: partSize.height,
          partType: partType,
          positionTop: positionTop,
          positionLeft: positionLeft,
        }));
      // Append to creation layout
      vm.$menuCreationLayout.append(newItem);
      // Init selectable creation layout
      vm.$menuCreationLayout.selectable({
        selected: (event, ui) => {
          $(ui.selected)
            .addClass("ui-selected")
            .siblings()
            .removeClass("ui-selected");
          // Wait for UI refresh
          vm.$nextTick(() => {
            if ($(ui.selected).hasClass('menu-creation-item')) {
              // Only allow dragable + resize on selected menu item
              if ($(ui.selected).hasClass('ui-selected')) {
                // Init/enable resize
                if ($(ui.selected).is('.ui-resizable')) {
                  $(ui.selected).resizable({ disabled: false });
                } else {
                  $(ui.selected).resizable({
                    disabled: false,
                    resize: (event, ui) => {
                      vm.renderHoveringItemOnResize(ui);
                    },
                    stop: (event, ui) => {
                      vm.removeHoveringItem();
                      vm.resizeItem(ui);
                    },
                  });
                }
                // Init/enable dragable
                if ($(ui.selected).is('.ui-draggable')) {
                  $(ui.selected).draggable({ disabled: false });
                } else {
                  $(ui.selected).draggable({
                    disabled: false,
                    containment: `#${MENU_CREATION_LAYOUT_ID}`,
                    drag: (event, ui) => {
                      const partData: PartData = ui.helper.data();
                      vm.renderHoveringItemOnDrag(ui, partData.width, partData.height);
                    },
                    stop: (event, ui) => {
                      vm.removeHoveringItem();
                      vm.moveItem(ui);
                    },
                  });
                }
              } else {
                // Disable dragable + resize on unselected menu item
                $(ui.selected)
                  .resizable({ disabled: true })
                  .draggable({ disabled: true });
              }
            }
          });
        },
        unselected: (event, ui) => {
          // Disable dragable + resize on unselected menu item
          if ($(ui.unselected).hasClass('menu-creation-item')) {
            $(ui.unselected)
              .resizable({ disabled: true })
              .draggable({ disabled: true });
          }
        }
      });
    }

    /**
     * Render hovering highlight effect on drag
     * @param item
     */
    private renderHoveringItemOnDrag(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      // Parent layout must have position: relative for item.position to be corrected
      const vm = this;
      // Calculate highlight div position
      let positionTop: number = item.position.top > 0 ? Math.round(item.position.top / CELL_SIZE) * CELL_SIZE : 0;
      let positionLeft: number = item.position.left > 0 ? Math.round(item.position.left / CELL_SIZE) * CELL_SIZE : 0;
      vm.renderHoveringItem(width, height, positionTop, positionLeft);
    }

    /**
     * Render hovering highlight effect on resize
     * @param item
     * @param minWidth
     * @param minHeight
     */
    private renderHoveringItemOnResize(item: JQueryUI.ResizableUIParams) {
      // Parent layout must have position: relative for item.position to be corrected
      const vm = this;
      const partData: PartData = item.element.data();
      // Calculate highlight div size
      const width: number = item.element.width() > partData.minWidth ? Math.ceil(item.element.width() / CELL_SIZE) * CELL_SIZE : partData.minWidth;
      const height: number = item.element.height() > partData.minHeight ? Math.ceil(item.element.height() / CELL_SIZE) * CELL_SIZE : partData.minHeight;
      vm.renderHoveringItem(width, height, partData.positionTop, partData.positionLeft);
    }

    /**
     * Render hovering highlight effect
     * @param width
     * @param height
     * @param positionTop
     * @param positionLeft
     */
    private renderHoveringItem(width: number, height: number, positionTop: number, positionLeft: number) {
      const vm = this;
      // If not existed, create new highlight div
      if (!vm.$hoverHighlight) {
        vm.$hoverHighlight = $("<div>", { id: ITEM_HIGHLIGHT_ID, "class": "menu-creation-item-highlight" });
      }
      // Set more attr (highlight width, height, position)
      vm.$hoverHighlight
        .outerWidth(width)
        .outerHeight(height)
        .css({ 'top': `${positionTop}px`, 'left': `${positionLeft}px` });
      // Append to creation layout
      vm.$menuCreationLayout.append(vm.$hoverHighlight);
    }

    /**
     * Remove hovering highlight effect on drag stop
     */
    private removeHoveringItem() {
      const vm = this;
      vm.$hoverHighlight.remove();
    }

    /**
     * Resize item on stop resizing
     * @param item
     */
    private resizeItem(item: JQueryUI.ResizableUIParams) {
      const partData: PartData = item.element.data();
      // Calculate highlight div size
      const width: number = item.element.width() > partData.minWidth ? Math.ceil(item.element.width() / CELL_SIZE) * CELL_SIZE : partData.minWidth;
      const height: number = item.element.height() > partData.minHeight ? Math.ceil(item.element.height() / CELL_SIZE) * CELL_SIZE : partData.minHeight;
      item.element
        // Set more attr (highlight width, height, position)
        .outerWidth(width)
        .outerHeight(height)
        .css({ 'top': `${partData.positionTop}px`, 'left': `${partData.positionLeft}px` })
        // Update item data object
        .data(new PartData({
          width: width,
          height: height,
          minWidth: partData.minWidth,
          minHeight: partData.minHeight,
          partType: partData.partType,
          positionTop: partData.positionTop,
          positionLeft: partData.positionLeft,
        }));
    }

    /**
     * Move item on stop dragging
     * @param item
     */
    private moveItem(item: JQueryUI.DraggableEventUIParams) {
      const partData: PartData = item.helper.data();
      // Calculate highlight div position
      const positionTop: number = item.position.top > 0 ? Math.round(item.position.top / CELL_SIZE) * CELL_SIZE : 0;
      const positionLeft: number = item.position.left > 0 ? Math.round(item.position.left / CELL_SIZE) * CELL_SIZE : 0;
      item.helper
        // Set more attr (highlight width, height, position)
        .css({ 'top': `${positionTop}px`, 'left': `${positionLeft}px` })
        // Update item data object
        .data(new PartData({
          width: partData.width,
          height: partData.height,
          minWidth: partData.minWidth,
          minHeight: partData.minHeight,
          partType: partData.partType,
          positionTop: positionTop,
          positionLeft: positionLeft,
        }));
    }

    /**
     * Get part size by type
     * @param partType
     */
    private getPartSize(partType: string): PartSize {
      switch (partType) {
        case MenuPartType.PART_MENU:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_LABEL:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_LINK:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_ATTACHMENT:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_IMAGE:
          // 2 x 2 cell
          return new PartSize({ width: 80, height: 80 });
        case MenuPartType.PART_ARROW:
          // 2 x 2 cell
          return new PartSize({ width: 80, height: 80 });
        default:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
      }
    }

    public test() {

    }

  }

  class PartData {
    width: number;
    height: number;
    minWidth: number;
    minHeight: number;
    partType: string;
    positionTop: number;
    positionLeft: number;

    constructor(init?: Partial<PartData>) {
      $.extend(this, init);
    }
  }

  class PartSize {
    width: number;
    height: number;

    constructor(init?: Partial<PartSize>) {
      $.extend(this, init);
    }
  }

  enum MenuPartType {
    PART_MENU = '1',
    PART_LABEL = '2',
    PART_LINK = '3',
    PART_ATTACHMENT = '4',
    PART_IMAGE = '5',
    PART_ARROW = '6',
  }

}