const checkboxes = document.querySelectorAll('.extension input[type="checkbox"]');
checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', () => {
        const isChecked = checkbox.checked;
        const extensionId = checkbox.getAttribute('data-extension-id');
        updateExtensionStatus(extensionId, isChecked);
    });
});

// 수정
async function updateExtensionStatus(id, isChecked) {
    const response = await fetch(`/extension/v1/extensions/check/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(isChecked)
    });

    const responseData = await response.json();

    if (responseData.result) {
        console.log('Extension status updated successfully.');
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Failed to save',
            text: responseData.message
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
        if (savedExtension) {
            const chipElement = createChipElement(customExtension, savedExtension);
            chipsContainer.appendChild(chipElement);
            customExtensionInput.value = '';

            const closeButton = document.createElement('i');
            closeButton.classList.add('close', 'material-icons');
            closeButton.textContent = 'close';
            closeButton.addEventListener('click', async () => {
                const deleteResponse = await deleteExtension(savedExtension);
                if (deleteResponse) {
                    chipsContainer.removeChild(chipElement);
                }
            });
            chipElement.appendChild(closeButton);
        }
    }
});

// 저장 서버 전송
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

            const responseData = await response.json();
            if (responseData.result) {
                return extensionName;
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Failed to save',
                    text: responseData.message
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
            text: '확장자는 20자 이하만 저장 가능합니다.',
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

// 삭제 서버 전송
async function deleteExtension(extensionId) {
    const response = await fetch(`/extension/v1/extensions/${extensionId}`, {
        method: 'DELETE',
    });

    if (response.ok) {
        console.log(`Extension ${extensionId} deleted successfully.`);
        return true;
    } else {
        console.error(`Failed to delete extension ${extensionId}.`);
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