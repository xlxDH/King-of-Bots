<template>
    <div class="playground">
        <GameMap/>
        <ContentField>
            游戏规则
        </ContentField>
    </div>
</template>

<script>

import GameMap from './GameMap.vue'
import ContentField from './ContentField.vue'

export default{
    components: {
        GameMap,
        ContentField,
    }
}

</script>

<style scoped>
div.playground{
    width: 60vw;
    height: 80vh;
    margin:3% auto;
}
</style>