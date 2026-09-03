const express = require('express');

const app = express();
const PORT = 8080;

app.use(express.json());

let users = [];
let nextUserId = 1;

app.get('/health', (req, res) => {
    res.status(200).json({
        status: 'UP'
    });
});

app.post('/api/users', (req, res) => {
    const { name, email } = req.body;

    if (!name || !email) {
        return res.status(400).json({
            message: 'name and email are required'
        });
    }

    const user = {
        id: nextUserId++,
        name,
        email
    };

    users.push(user);

    return res.status(201).json(user);
});

app.get('/api/users/:id', (req, res) => {
    const userId = Number(req.params.id);

    const user = users.find(user => user.id === userId);

    if (!user) {
        return res.status(404).json({
            message: 'User not found'
        });
    }

    return res.status(200).json(user);
});

app.listen(PORT, () => {
    console.log(`API server running at http://localhost:${PORT}`);
});