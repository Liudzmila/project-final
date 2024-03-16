function addTagFromInput() {
    const tagsInput = document.getElementById('tags');
    const tagValue = tagsInput.value.trim(); // Получаем введенный тег и удаляем лишние пробелы

    if (tagValue !== '') {
        const displayedTags = document.getElementById('displayedTags');
        const tagElement = document.createElement('div');
        tagElement.innerHTML = '<span>' + tagValue + '</span>' +
            '<button type="button" class="remove-tag" onclick="removeTag(this)">&times;</button>';

        displayedTags.appendChild(tagElement);

        const createdTagsInput = document.getElementById('createdTags');
        createdTagsInput.value += (createdTagsInput.value ? ',' : '') + tagValue;

        tagsInput.value = '';
    }
}

function removeTag(element) {
    const tagValue = element.previousElementSibling.textContent.trim();

    element.parentElement.remove();

    const tagsToRemoveInput = document.getElementById('tagsToRemove');
    tagsToRemoveInput.value += (tagsToRemoveInput.value ? ',' : '') + tagValue;
}
