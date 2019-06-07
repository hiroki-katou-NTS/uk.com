<template>
    <div class="documentspluginsvalidation">
        <h5>1. {{'sample' | i18n}}</h5>
        <div class="row justify-content-md-center">
            <div class="col-md-4">
                <nts-number-editor
                    name='username'
                    v-model="model.username" />
                <nts-input-password
                    name='password'
                    v-model="model.password" />
            </div>
        </div>
        <markdown />
    </div>
</template>