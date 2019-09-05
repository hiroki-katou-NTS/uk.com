<template>
<div class="kdws03g">
    <h3>Hello {{title | i18n}} component!</h3>
    <nts-text-editor
        name='title'
        v-model='title' />
</div>
</template>