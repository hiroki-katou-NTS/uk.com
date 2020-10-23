/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.e.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {

    layoutClone: JQuery;

    created() {
    }

    mounted() {

      $('.box-item').draggable({
        revert: true,
        revertDuration: 0,
        stack: ".draggable",
        cursor: "pointer",
        containment: '#content_layout'
      });
    
      $("#container2").droppable({
        drop: function(event: any, ui) {
          console.log(event);
          let itemid = $(event.originalEvent.toElement).attr("itemid");
          $('.box-item').each(function() {
            if ($(this).attr("itemid") === itemid) {
              // $(this).addClass("item_opacity");
              $(".test").show();
              // $(this).addClass("item_opacity").clone().removeClass("box-item item_opacity").addClass("item_choose fix_style_item").appendTo("#container2");
              $(this).addClass("item_opacity").draggable({
                disabled: true
              });
            }
          });
        }
      });
    }

    // Click button x item content 2
    removeItem() {
      $(".removeItemChoose").on("click", function(e) {
        // Get attribute item remove
        let itemAttr = $(this).parents(".item_choose").attr("itemid");
        // Remove item in content 2
        $(this).parents(".item_choose").remove();
        // Find item drag in content 1
        let itemDrag: JQuery = $("#container1").find("[itemid='" + itemAttr + "']");
        // Enable item drag in content 1
        $(itemDrag).removeClass("item_opacity").draggable({
          disabled: false
        });
      });
    }
   
    close() {
      nts.uk.ui.windows.close();
    }
  }
}