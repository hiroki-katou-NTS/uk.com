<template>
<div class="kafs12b">
    <h3>Hello {{title | i18n}} component!</h3>
    <nts-text-editor
        name='title'
        v-model='title' />
</div>
</template>