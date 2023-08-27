    const checkboxes = document.querySelectorAll('.extension input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', () => {
            const isChecked = checkbox.checked;
            const extensionId = checkbox.getAttribute('data-extension-id');
            updateExtensionStatus(extensionId, isChecked);
        });
    });

    async function updateExtensionStatus(id, isChecked) {
        const response = await fetch(`/extension/v1/extensions/check/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(isChecked)
        });

        const responseData = await response.json(); // Parse JSON response

        if (responseData.result) {
            console.log('Extension status updated successfully.');
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Failed to save',
                text: responseData.message // Use parsed error message
            });
            return;
        }
    }
    // 저장 / 삭제

    const addExtensionButton = document.getElementById('addExtensionButton');
    const customExtensionInput = document.getElementById('customExtension');
    const chipsContainer = document.getElementById('chipsContainer');

    addExtensionButton.addEventListener('click', async () => {
        const customExtension = customExtensionInput.value.trim();

        if (customExtension !== '') {
            const savedExtension = await saveCustomExtension(customExtension);
            console.log(savedExtension)
            if (savedExtension) {
                const chipElement = createChipElement(customExtension, savedExtension);
                chipsContainer.appendChild(chipElement);
                customExtensionInput.value = '';
                const closeButton = document.createElement('i');
                closeButton.classList.add('close', 'material-icons');
                closeButton.textContent = 'close';
                closeButton.dataset.extensionId = savedExtension;
                closeButton.addEventListener('click', async (event) => {
                    const deleteResponse = await deleteExtension(Number(event.target.dataset.extensionId));
                    if (deleteResponse) {
                        chipsContainer.removeChild(chipElement);
                    }
                });
                chipElement.appendChild(closeButton);
            }
        }
    });

    async function saveCustomExtension(extensionName) {
        var regex = /^[a-zA-Z0-9]+$/;
        if (extensionName.length <= 20) {
            if (regex.test(extensionName)) {
                const response = await fetch('/extension/v1/extensions', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        name: extensionName
                    })
                });

                const responseData = await response.json(); // Parse JSON response
                if (responseData.result) {
                    return responseData.data;
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Failed to save',
                        text: responseData.message // Use parsed error message
                    });
                    return;
                }
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed to save',
                    text: '특수문자 및 한글은 저장 불가능합니다.',
                });
                return;
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Failed to save',
                text: '확장자는 20자 이하만 저장 가능합니다..',
            });
            return;
        }
    }

    function createChipElement(customExtension, savedExtension) {
        const chipElement = document.createElement('div');
        chipElement.className = 'chip';
        chipElement.textContent = customExtension;
        chipElement.dataset.extensionId = savedExtension;
        return chipElement;
    }

    async function onClickDelete(event) {
        const extensionId = event.target.dataset.extensionId;
        await deleteExtension(extensionId);
    }

    async function deleteExtension(extensionId) {
        const response = await fetch(`/extension/v1/extensions/${extensionId}`, {
            method: 'DELETE',
        });
        const responseData = await response.json();
        if (responseData.result) {
            console.log(`Extension ${extensionId} deleted successfully.`);
            return true;
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Failed to delete',
                text: responseData.message
            });
            return false;
        }
    }


    const uploadButton = document.getElementById('uploadButton');
    const fileInput = document.getElementById('fileInput');

    uploadButton.addEventListener('click', async () => {
    const selectedFile = fileInput.files[0];
    if (!selectedFile) {
        Swal.fire({
            icon: 'error',
            title: 'File not selected',
            text: '저장할 파일을 선택 해주세요.'
        });
        return;
    }

    const formData = new FormData();
    formData.append('file', selectedFile);

    try {
        const response = await fetch('/extension/v1/extensions/upload', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            const responseData = await response.json();
            if (responseData.result) {
                Swal.fire({
                    icon: 'success',
                    title: 'File uploaded',
                    text: '파일 저장에 성공했습니다.'
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Upload failed',
                    text: responseData.message
                });
            }
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Upload failed',
                text: 'Failed to upload the file.'
            });
        }
    } catch (error) {
        console.error('Error during file upload:', error);
    }
    });
