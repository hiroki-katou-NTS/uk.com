ko.components.register('kaf022-b', {
    viewModel: {
        createViewModel: function(params, componentInfo) {
            let vm = {
                // Data: value is either null, 'like', or 'dislike'
                chosenValue: params.value,
                // Behaviors
                like: function() { vm.chosenValue('like'); },
                dislike: function() { vm.chosenValue('dislike'); }
            };

            return vm;
        }
    },
    template: `<div class="like-or-dislike" data-bind="visible: !chosenValue()">
        <button data-bind="click: like">Like it</button>
        <button data-bind="click: dislike">Dislike it</button>
    </div>
    <div class="result" data-bind="visible: chosenValue">
        You <strong data-bind="text: chosenValue"></strong> it
    </div>`
});